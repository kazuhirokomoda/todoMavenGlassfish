
package todo.app.common.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author kkomoda
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        
        /*
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        // following code can be used to customize Jersey 2.0 JSON provider:
        try {
            //Class jsonProvider = Class.forName("org.glassfish.jersey.jackson.JacksonFeature");
            Class jsonProvider = Class.forName("org.glassfish.jersey.moxy.json.MoxyJsonFeature");
            // Class jsonProvider = Class.forName("org.glassfish.jersey.jettison.JettisonFeature");
            resources.add(jsonProvider);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        addRestResourceClasses(resources);
        return resources;
        */

        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
        
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(todo.app.common.exception.BusinessExceptionMapper.class);
        resources.add(todo.app.common.exception.ConstraintViolationExceptionMapper.class);
        resources.add(todo.app.common.exception.ResourceNotFoundExceptionMapper.class);
        resources.add(todo.app.todo.TodoResource.class);
    }
    
}
