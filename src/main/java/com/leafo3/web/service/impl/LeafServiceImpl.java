package com.leafo3.web.service.impl;

import com.leafo3.dto.request.DamageClassChart;
import com.leafo3.dto.response.error.InternalServerError;
import com.leafo3.web.core.util.Leafo3Utils;
import com.leafo3.dto.entity.LeafInfoFileContent;
import com.leafo3.dto.entity.Page;
import com.leafo3.dto.entity.PieChartData;
import com.leafo3.web.data.entity.Leaf;
import com.leafo3.web.data.repository.LeafRepository;
import com.leafo3.web.core.file.FileService;
import com.leafo3.web.service.LeafService;
import com.leafo3.dto.response.error.ErrorCode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public class LeafServiceImpl implements LeafService {

    @Inject
    private FileService fileService;

    @Inject
    private LeafRepository leafRepository;

    @Inject
    private Leafo3Utils leafo3Utils;

    private final Logger log = Logger.getLogger(getClass());

    @Override
    public Response create(MultipartFormDataInput input) throws Exception {
        Response response = null;

        LeafInfoFileContent content = getFileContent(input);
        //create the leaf
        final Leaf leaf = getLeaf(content);

        //save image
        InputStream leafStream = content.getLeafStream();
        log.info("Leaf image stream is null " + (leafStream == null));
        String imagePath;
        Object[] result = null;
        imagePath = fileService.saveImage(leafStream, leaf.getId(), FileService.FileType.ORIGINAL);
        log.info("Saved original image");
        log.info("Processing original image");
        result = fileService.processImage(leaf.getId());
        //get processed image path
        final String processedImagePath = (String) result[0];
        final Integer percentage = ((Double) result[1]).intValue();

        //get damage class
        final int damageClass = getDamageClass(percentage);
        //set leaf properties
        leaf.setPercentage(percentage);
        leaf.setDamageClass(damageClass);
        leaf.setCreationDate(leafo3Utils.formatDate(new Date()));
        //add the new leaf
        leafRepository.createNewLeaf(leaf);
        response = Response.ok(leaf).build();
        return response;

    }

    @Override
    public Response records(String email, int record, int pageNumber) throws Exception {
        Response response = null;
        CurrentRecords cRecord = getCurrentRecords(record);
        Page<Leaf> leafs = null;
        switch (cRecord) {
            case USER:
                leafs = leafRepository.getUserLeafs(email, pageNumber);
                break;
            case NEWEST:
                leafs = leafRepository.getNewestLeaf(pageNumber);
                break;
            case DAMAGED:
                leafs = leafRepository.getMoreDamageLeafs(pageNumber);
                break;
        }
        //TODO: leafs
        response = Response.ok(leafs).build();
        return response;

    }

    @Override
    public Response find(String leafId) throws Exception {
        Response response = null;
        //search for the leaf 
        Leaf leaf = null;
        leaf = leafRepository.findLeaf(leafId);
        if (leaf == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new InternalServerError("Leaf id is not valid", leafo3Utils.formatDate(new Date()), ErrorCode.INVALID_LEAF_ID))
                    .build();
        }

        return Response.ok(leaf).build();
    }

    @Override
    public Response getDamageClassChartData() throws Exception {
        Response response = null;

        //get the map
        List<Object[]> map = leafRepository.getDamageByClassChartData();
        List<DamageClassChart> list = new ArrayList();
        for (Object[] model : map)
            list.add(new DamageClassChart(((BigInteger) model[0]).intValue(), (String) model[1], ((BigDecimal) model[2]).doubleValue()));
        response = Response.ok(list).build();
        return response;
    }

    @Override
    public Response getPieChartData() throws Exception {
        Response response = null;
        List<Object[]> map = leafRepository.getPieChartData();
        List<PieChartData> list = new ArrayList();
        for (Object[] model : map)
            list.add(new PieChartData(((BigInteger) model[0]).intValue(), (String) model[1]));
        response = Response.ok(list).build();

        return response;
    }

    private Leaf getLeaf(LeafInfoFileContent content) {
        Leaf leaf = new Leaf();
        leaf.setTitle(content.getTitle());
        leaf.setComment(content.getComment());
        leaf.setLocation(content.getLocation());
        leaf.setUploadedBy(content.getEmail());
        leaf.setIsoCode(content.getIsoCode());
        return leaf;
    }

    private int getDamageClass(double percentage) {
        if (percentage == 0.0) {
            return 1;
        } else if (percentage >= 1 && percentage <= 6) {
            return 2;
        } else if (percentage >= 7 && percentage <= 25) {
            return 3;
        } else if (percentage >= 26 && percentage <= 50) {
            return 4;
        } else if (percentage >= 51 && percentage <= 75) {
            return 5;
        } else//(percentage >= 76 && percentage <= 100)
        {
            return 6;
        }
    }

    private LeafInfoFileContent getFileContent(MultipartFormDataInput input) throws IOException {
        LeafInfoFileContent content = new LeafInfoFileContent();
        Map<String, List<InputPart>> map = input.getFormDataMap();
        List<InputPart> fileParts = map.get("file");
        List<InputPart> locationParts = map.get("location");
        List<InputPart> emailParts = map.get("email");
        List<InputPart> titleParts = map.get("title");
        List<InputPart> commentParts = map.get("comment");
        List<InputPart> isoParts = map.get("iso");

        InputStream file = null;
        String location = null, email = null, title = null, comment = null, iso = null;
        for (InputPart part : fileParts) {
            file = part.getBody(InputStream.class, null);
        }

        for (InputPart part : locationParts) {
            location = part.getBodyAsString();
        }

        for (InputPart part : emailParts) {
            email = part.getBodyAsString();
        }

        for (InputPart part : titleParts) {
            title = part.getBodyAsString();
        }

        for (InputPart part : commentParts) {
            comment = part.getBodyAsString();
        }

        for (InputPart part : isoParts) {
            iso = part.getBodyAsString();
        }

        content.setComment(comment);
        content.setEmail(email);
        content.setIsoCode(iso);
        content.setLeafStream(file);
        content.setLocation(location);
        content.setTitle(title);
        return content;
    }

    @Override
    public Response getLeafs() throws Exception {
        return Response.ok(leafRepository.getLeafs())
                .build();
    }

    @Override
    public Response getLeafImage(String leafId, int fileType) throws Exception {
        InputStream stream = null;
        switch (fileType) {
            case 1:
                //normal image
                stream = fileService.getStream(FileService.FileType.ORIGINAL, leafId);
                break;
            case 0:
                //processed image
                stream = fileService.getStream(FileService.FileType.PROCESSED, leafId);
                break;
        }
        return Response.ok(stream).build();
    }

    @Override
    public Response getAndroidApplicationFile() throws Exception {
        File file = fileService.getAndroidApplicationFile();
        return Response.ok((Object) file)
                .header("Content-Disposition", "attachment;filename=\"leafo3.apk\"")
                .build();
    }

    private enum CurrentRecords {

        USER, NEWEST, DAMAGED
    }

    private CurrentRecords getCurrentRecords(int rec) {
        CurrentRecords cRecord = CurrentRecords.USER;
        switch (rec) {
            case 0:
                cRecord = CurrentRecords.USER;
                break;
            case 1:
                cRecord = CurrentRecords.NEWEST;
                break;
            case 2:
                cRecord = CurrentRecords.DAMAGED;
                break;
        }
        return cRecord;
    }
}
