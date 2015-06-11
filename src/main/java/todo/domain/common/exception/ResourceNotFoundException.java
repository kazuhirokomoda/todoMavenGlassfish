
package todo.domain.common.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author kkomoda
 */

@ApplicationException
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
