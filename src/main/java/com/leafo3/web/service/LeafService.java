package com.leafo3.web.service;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public interface LeafService {
    public Response create(MultipartFormDataInput input) throws Exception;
    public Response records(String email, int record, int pageNumber) throws Exception;
    public Response find(String leafId) throws Exception;
    public Response getDamageClassChartData() throws Exception;
    public Response getPieChartData() throws Exception;
    public Response getLeafs()throws Exception;
    public Response getLeafImage(String leafId, int fileType)throws Exception;
    public Response getAndroidApplicationFile() throws Exception;
}
