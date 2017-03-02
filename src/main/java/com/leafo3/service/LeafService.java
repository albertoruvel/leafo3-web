package com.leafo3.service;

import com.leafo3.data.dto.LeafInfoFileContent;
import com.leafo3.exception.LeafO3Exception;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
public interface LeafService {
    public Response create(MultipartFormDataInput input) throws LeafO3Exception;
    public Response records(String email, int record, int pageNumber) throws LeafO3Exception; 
    public Response find(String leafId) throws LeafO3Exception; 
    public Response getDamageClassChartData() throws LeafO3Exception; 
    public Response getPieChartData() throws LeafO3Exception; 

    public Response getLeafs()throws LeafO3Exception;

    public Response getLeafImage(String leafId, int fileType)throws LeafO3Exception;

    public Response getAndroidApplicationFile() throws LeafO3Exception;
}
