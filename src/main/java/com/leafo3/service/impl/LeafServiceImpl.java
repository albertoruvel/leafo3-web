package com.leafo3.service.impl;

import com.leafo3.data.dto.DamageClassChartModel;
import com.leafo3.data.dto.FindResourceResponse;
import com.leafo3.data.dto.LeafInfoFileContent;
import com.leafo3.data.dto.LeafsResponse;
import com.leafo3.data.dto.Page;
import com.leafo3.data.entity.Leaf;
import com.leafo3.data.repository.LeafRepository;
import com.leafo3.exception.DataAccessException;
import com.leafo3.exception.LeafO3Exception;
import com.leafo3.service.FileService;
import com.leafo3.service.LeafService;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
public class LeafServiceImpl implements LeafService {

    @Inject
    private FileService fileService;

    @Inject
    private LeafRepository leafRepository;

    @Inject
    private Logger log;

    @Override
    public Response create(MultipartFormDataInput input) throws LeafO3Exception {
        Response response = null;

        try {
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
            leaf.setCreationDate(new SimpleDateFormat("MM-dd-yyyy HH-mm-ss").format(new Date()));
            //add the new leaf
            leafRepository.createNewLeaf(leaf);

            //create the new response
            FindResourceResponse resourceResponse = new FindResourceResponse(leaf, true);
            response = Response.ok(resourceResponse).build();
            return response;
        } catch (IOException ex) {
            throw new LeafO3Exception(ex.getMessage());
        } catch (DataAccessException ex) {
            throw new LeafO3Exception(ex.getMessage());
        }
    }

    @Override
    public Response records(String email, int record, int pageNumber) throws LeafO3Exception {
        Response response = null;

        CurrentRecords cRecord = getCurrentRecords(record);
        Page<Leaf> leafs = null;
        try {
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
            //create a leafs response
            LeafsResponse leafsResponse = new LeafsResponse();
            leafsResponse.setMessage("Success");
            leafsResponse.setSuccess(true);
            leafsResponse.setData(leafs);
            response = Response.ok(leafsResponse).build();
            return response;
        } catch (DataAccessException ex) {
            throw new LeafO3Exception(ex.getMessage());
        }

    }

    @Override
    public Response find(String leafId) throws LeafO3Exception {
        Response response = null;
        //search for the leaf 
        Leaf leaf = null;

        try {
            leaf = leafRepository.findLeaf(leafId);
            FindResourceResponse<Leaf> resourceResponse = null;
            if (leaf != null) {
                resourceResponse = new FindResourceResponse<Leaf>(leaf, true);
                response = Response.ok(resourceResponse).build();
            } else {
                resourceResponse = new FindResourceResponse<Leaf>(leaf, false);
                response = Response.ok(resourceResponse).build();
            }
            return response;
        } catch (Exception ex) {
            throw new LeafO3Exception(ex.getMessage());
        }

    }

    @Override
    public Response getDamageClassChartData() throws LeafO3Exception {
        try {
            Response response = null;

            //get the map 
            List<Map<String, Object>> map = leafRepository.getDamageByClassChartData();

            DamageClassChartModel model = new DamageClassChartModel();
            model.setData(map);
            model.setSuccess(true);

            response = Response.ok(model).build();

            return response;
        } catch (DataAccessException ex) {
            throw new LeafO3Exception(ex.getMessage());
        }
    }

    @Override
    public Response getPieChartData() throws LeafO3Exception {
        try {
            Response response = null;
            List<Map<String, Object>> map = leafRepository.getPieChartData();
            DamageClassChartModel model = new DamageClassChartModel();
            model.setData(map);
            model.setSuccess(true);

            response = Response.ok(model).build();

            return response;
        } catch (DataAccessException ex) {
            throw new LeafO3Exception(ex.getMessage());
        }
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
    public Response getLeafs() throws LeafO3Exception {
        try{
            return Response.ok(leafRepository.getLeafs())
                    .build();
        }catch(DataAccessException ex){
            throw new LeafO3Exception(ex.getMessage());
        }
    }

    public Response getLeafImage(String leafId, int fileType) throws LeafO3Exception {
        try{
            InputStream stream = null;
            switch(fileType){
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
        }catch(IOException ex){
            throw new LeafO3Exception(ex.getMessage());
        }
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
