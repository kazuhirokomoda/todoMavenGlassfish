
package todo.app.common.exception;

import java.util.Arrays;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import todo.domain.common.exception.ResourceNotFoundException;

/**
 *
 * @author kkomoda
 */

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException>{
    
    @Override
    public Response toResponse(ResourceNotFoundException exception) {
        
        // simple example
        //http://d.hatena.ne.jp/shin/20101004/p2

        ErrorModel errorModel = new ErrorModel(Arrays.asList(exception.getMessage()));
        //System.out.println(errorModel.getErrorMessages());
        System.out.println("errorModel[0]: " + errorModel.getErrorMessages().get(0));
        return Response.status(Response.Status.NOT_FOUND) // 404
                .entity(errorModel).build();
    }
    
}
