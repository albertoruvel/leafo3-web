package com.leafo3.web.resource;

import com.leafo3.web.service.LeafService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/leafs")
public class LeafResource {
    
    @Inject
    private LeafService service;

    @Path("/create")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLeaf(MultipartFormDataInput input)throws Exception{
        return service.create(input);
    }

    @GET
    @Path("/records")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecords(
            @QueryParam("email")String email, 
            @QueryParam("record")int record, 
            @QueryParam("page_number")int pageNumber)throws Exception{
        return service.records(email, record, pageNumber); 
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeafs()throws Exception{
        return service.getLeafs();
    }

    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findLeaf(
            @QueryParam("id")String leafId)throws Exception{
        return service.find(leafId); 
    }
    
    @GET
    @Path("/classChart")
    @Produces(MediaType.APPLICATION_JSON)
    public Response classChart()throws Exception{
        return service.getDamageClassChartData(); 
    }

    @GET
    @Path("/pieChart")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pieChart()throws Exception{
        return service.getPieChartData(); 
    }
    
    @GET
    @Path("leafImage")
    @Produces("image/jpeg")
    public Response getLeafImage(@QueryParam("leafId")String leafId, @DefaultValue("1") @QueryParam("imageType")int fileType)throws Exception{
        return service.getLeafImage(leafId, fileType);
    }
    
    @GET
    @Path("apk")
    @Produces("application/vnd.android.package-archive")
    public Response downloadApk()throws Exception{
        return service.getAndroidApplicationFile(); 
    }
}
