
package todo.domain.service.todo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import todo.domain.common.exception.ResourceNotFoundException;
import todo.domain.model.Todo;

/**
 *
 * @author kkomoda
 */
public class TodoServiceTest {
    
    private EJBContainer container;
    private Context context;
    
    public TodoServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // https://getsatisfaction.com/javaee6/topics/yet_another_chapter_6_ejb_problem
        // http://stackoverflow.com/questions/16145613/jpa-ejb-testing-with-embedded-glassfish-v3
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(EJBContainer.MODULES, new File("target/classes")); // "target/classes" "build/jar" "/Users/kkomoda/.jenkins/jobs/todoMavenGlassfish_Build/workspace/target/classes"
        properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "/Applications/NetBeans/glassfish-4.1/glassfish"); // ./src/test/glassfish
        properties.put("org.glassfish.ejb.embedded.glassfish.configuration.file", "/Applications/NetBeans/glassfish-4.1/glassfish/domains/domain1/config/domain.xml"); // ./src/test/glassfish/domains/domain1/config/domain.xml
        
        container = EJBContainer.createEJBContainer(properties); // javax.ejb.embeddable.
        context = container.getContext();
    }
    
    @After
    public void tearDown() {
        container.close();
    }

    /**
     * Test of findAll method, of class TodoService.
     */
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        TodoService instance = (TodoService)context.lookup("java:global/classes/TodoService");

        // findAll
        List<Todo> result = instance.findAll();

        // assert
        System.out.println(result);
        assertNotNull(result);

    }

    /**
     * Test of findOne method, of class TodoService.
     */
    @Test
    public void testFindOne() throws Exception {
        System.out.println("findOne");
        TodoService instance = (TodoService)context.lookup("java:global/classes/TodoService");
        
        // findOne
        Long todoId = 3L; // look inside DB and choose a number
        Todo result = instance.findOne(todoId);

        // assert
        System.out.println(result);
        assertNotNull(result);
        assertNotNull(result.getTodoId());
        assertEquals(true, result.isFinished());
        assertEquals("JUnit test", result.getTodoTitle());

    }

    /**
     * Test of create method, of class TodoService.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        TodoService instance = (TodoService)context.lookup("java:global/classes/TodoService");
        
        // create
        Todo todo = new Todo();
        todo.setTodoTitle("eat breakfast");
        Todo result = instance.create(todo);
        
        // assert
        System.out.println("created: " + result);
        assertNotNull(result);
        assertNotNull(result.getTodoId());
        assertEquals(todo.getTodoTitle(), result.getTodoTitle());
        assertEquals(false, result.isFinished());
        assertEquals(1, result.getVersion());
        
        // delete
        instance.delete(result.getTodoId());
    }

    /**
     * Test of finish method, of class TodoService.
     */
    /*
    @Test
    public void testFinish() throws Exception {
        System.out.println("finish");
        
        Integer todoId = null;
        TodoService instance = (TodoService)container.getContext().lookup("java:global/classes/TodoService");
        Todo expResult = null;
        Todo result = instance.finish(todoId);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */

    /**
     * Test of update method, of class TodoService.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        TodoService instance = (TodoService)container.getContext().lookup("java:global/classes/TodoService");

        // create
        Todo todo = new Todo();
        todo.setTodoTitle("eat breakfast");
        Todo result = instance.create(todo);
        assertNotNull(result);
        assertNotNull(result.getTodoId());

        // update
        Long todoId = result.getTodoId();
        System.out.println("update [" + todoId + "]");
        Todo tmpTodo = instance.findOne(todoId);
        tmpTodo.setTodoTitle("eat lunch");
        tmpTodo.setFinished(true);
        Todo updatedTodo = instance.update(tmpTodo);

        // assert
        assertEquals("eat lunch", updatedTodo.getTodoTitle());
        assertEquals(true, updatedTodo.isFinished());
        //assertEquals(2, result.getVersion());

        // delete
        //Long todoId = result.getTodoId();
        System.out.println("delete [" + todoId + "]");
        instance.delete(todoId);
    }
    
    /**
     * Test of delete method, of class TodoService.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        TodoService instance = (TodoService)context.lookup("java:global/classes/TodoService");

        // create
        Todo todo = new Todo();
        todo.setTodoTitle("eat breakfast");
        Todo result = instance.create(todo);
        assertNotNull(result);
        assertNotNull(result.getTodoId());

        // delete
        Long todoId = result.getTodoId();
        System.out.println("delete [" + todoId + "]");
        instance.delete(todoId);

        // findOne
        try {
            instance.findOne(todoId);
            fail(todoId + " is not deleted!");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
