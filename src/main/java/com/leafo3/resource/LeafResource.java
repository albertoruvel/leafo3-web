package com.leafo3.resource;

import com.leafo3.exception.LeafO3Exception;
import com.leafo3.service.LeafService;
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

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
@Path("/leafs")
public class LeafResource {
    
    @Inject
    private LeafService service;

    @Path("/create")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLeaf(MultipartFormDataInput input)throws LeafO3Exception{
        return service.create(input);
    }

    @GET
    @Path("/records")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecords(
            @QueryParam("email")String email, 
            @QueryParam("record")int record, 
            @QueryParam("page_number")int pageNumber)throws LeafO3Exception{
        return service.records(email, record, pageNumber); 
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeafs()throws LeafO3Exception{
        return service.getLeafs();
    }

    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findLeaf(
            @QueryParam("id")String leafId)throws LeafO3Exception{
        return service.find(leafId); 
    }
    
    @GET
    @Path("/class_chart")
    @Produces(MediaType.APPLICATION_JSON)
    public Response classChart()throws LeafO3Exception{ 
        return service.getDamageClassChartData(); 
    }

    @GET
    @Path("/pie_chart")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pieChart()throws LeafO3Exception{ 
        return service.getPieChartData(); 
    }
    
    @GET
    @Path("leafImage")
    @Produces("image/jpeg")
    public Response getLeafImage(@QueryParam("leafId")String leafId, @DefaultValue("1") @QueryParam("imageType")int fileType)throws LeafO3Exception{
        return service.getLeafImage(leafId, fileType);
    }
    
    @GET
    @Path("apk")
    @Produces("application/vnd.android.package-archive")
    public Response downloadApk()throws LeafO3Exception{
        return service.getAndroidApplicationFile(); 
    }
}
