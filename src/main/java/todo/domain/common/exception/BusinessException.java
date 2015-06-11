
package todo.domain.common.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author kkomoda
 */

@ApplicationException
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
