
package todo.domain.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import javax.xml.bind.annotation.XmlRootElement;
import todo.domain.model.group.Create;

/**
 *
 * @author kkomoda
 */
@Entity
@Table(name = "todo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Todo.findAll", query = "SELECT t FROM Todo t"),
    @NamedQuery(name = "Todo.findByTodoId", query = "SELECT t FROM Todo t WHERE t.todoId = :todoId"),
    @NamedQuery(name = "Todo.findByTodoTitle", query = "SELECT t FROM Todo t WHERE t.todoTitle = :todoTitle"),
    @NamedQuery(name = "Todo.findByFinished", query = "SELECT t FROM Todo t WHERE t.finished = :finished"),
    @NamedQuery(name = "Todo.findByCreatedAt", query = "SELECT t FROM Todo t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "Todo.findByVersion", query = "SELECT t FROM Todo t WHERE t.version = :version")})
public class Todo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "todo_id")
    private Long todoId;
    
    @Basic(optional = false)
    @NotNull(groups={Default.class, Create.class})
    @Size(min = 1, max = 128, groups={Default.class, Create.class})
    @Column(name = "todo_title")
    private String todoTitle;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "finished")
    private boolean finished;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    @Version
    private int version;

    public Todo() {
    }

    public Todo(Long todoId) {
        this.todoId = todoId;
    }

    public Todo(Long todoId, String todoTitle, boolean finished, Date createdAt, int version) {
        this.todoId = todoId;
        this.todoTitle = todoTitle;
        this.finished = finished;
        this.createdAt = createdAt;
        this.version = version;
    }

    public Long getTodoId() {
        return todoId;
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (todoId != null ? todoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Todo)) {
            return false;
        }
        Todo other = (Todo) object;
        if ((this.todoId == null && other.todoId != null) || (this.todoId != null && !this.todoId.equals(other.todoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "todo.domain.model.Todo[ todoId=" + todoId + " ]";
    }
    
}
