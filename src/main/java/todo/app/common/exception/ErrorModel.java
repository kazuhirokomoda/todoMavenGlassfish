
package todo.app.common.exception;

import java.util.List;

/**
 *
 * @author kkomoda
 */

public class ErrorModel {

    private final List<String> errorMessages;
    

    public ErrorModel(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

   
}
