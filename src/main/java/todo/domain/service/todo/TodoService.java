
package todo.domain.service.todo;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import todo.domain.common.exception.BusinessException;
import todo.domain.common.exception.ResourceNotFoundException;
import todo.domain.model.Todo;

/**
 *
 * @author kkomoda
 */

@Stateless
public class TodoService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private static final long MAX_UNFINISHED_COUNT = 100;
    
    // JPAのエンティティを操作するEntityManagerをインジェクションする
    @PersistenceContext
    protected EntityManager entityManager;

    
    public List<Todo> findAll() {
        // 指定した”Todo.findAll”に対応するQueryはTodoクラスに@NamedQuery(name = "Todo.findAll", query = "SELECT t FROM Todo t")と定義されている
        TypedQuery<Todo> q = entityManager.createNamedQuery("Todo.findAll", Todo.class);
        return q.getResultList();
    }
    
    
    public Todo findOne(Long todoId) {
        Todo todo = entityManager.find(Todo.class, todoId);
        if (todo == null) {
            throw new ResourceNotFoundException("[E404] The requested Todo is not found. (id=" + todoId + ")");
        }
        return todo;
    }
    
    
    public Todo create(Todo todo) {
        
        // TODO: ここではcreateQueryメソッドで動的にJPQLを実行する例として実装しているが、
        // 毎回JPQLをコンパイルするオーバーヘッドがあるので、findAll同様にNamedQueryとして定義した方が高性能である。
        TypedQuery<Long> q = entityManager.createQuery("SELECT COUNT(x) FROM Todo x WHERE x.finished = :finished", Long.class)
                .setParameter("finished", false);
        long unfinishedCount = q.getSingleResult();
        if (unfinishedCount > MAX_UNFINISHED_COUNT) {
            throw new BusinessException("[E001] The count of un-finished Todo must not be over "
                    + MAX_UNFINISHED_COUNT + ".");
        }

        todo.setFinished(false);
        todo.setCreatedAt(new Date());
        
        // persistメソッドにエンティティを渡すことで、エンティティをEntityManagerの管理下に置く。
        // これにより、メソッド終了時のトランザクションコミットのタイミングでDBにinsert文が実行される。IDが設定される。insert用のSQLを書く必要がない。
        entityManager.persist(todo);
        return todo;

    }
    
    /*
    public Todo finish(Long todoId) {
        Todo todo = findOne(todoId);
        
        //if (todo.isFinished()) {
        //    throw new BusinessException("[E002] The requested Todo is already finished. (id=" + todoId + ")");
        //}
        
        //todo.setFinished(true);
        //todo.setTodoTitle("updated in TodoService.java");
                
        // 対象のエンティティに既にIDが設定されているため、
        // mergeメソッドでEntityManagerの管理下に置き、トランザクションコミット時にupdate文が実行される。
        entityManager.merge(todo);
        return todo;
    }
    */
    
    
    public Todo update(Todo todo) {          
        // 対象のエンティティに既にIDが設定されているため、
        // mergeメソッドでEntityManagerの管理下に置き、トランザクションコミット時にupdate文が実行される。
        entityManager.merge(todo);
        return todo;
    }

    
    public void delete(Long todoId) {
        Todo todo = findOne(todoId);
        // removeメソッドにエンティティを渡すことで、エンティティをEntityManagerの管理から除外する。
        entityManager.remove(todo);
    }

    
}
