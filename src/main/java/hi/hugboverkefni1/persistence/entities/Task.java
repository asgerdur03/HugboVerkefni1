package hi.hugboverkefni1.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


@Entity
@Table(name = "tasks")
public class Task{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String taskName;
    private String taskNote;


    private TaskStatus status;
    private TaskPriority priority;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name="category_id", nullable= true)
    private Category category;

    @Transient
    private Long categoryId;


    @ManyToOne
    private User user;


    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean favorite= false;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE" )
    private boolean Archived=false;

    public Task() {

    }

    public Task(String taskName, String taskNote) {
        this.taskName = taskName;
        this.taskNote = taskNote;
    }

    public Task(String taskName, String taskNote, TaskStatus status, TaskPriority priority, LocalDate dueDate, User user, Category category, boolean favorite, boolean isArchived) {
        this.taskName = taskName;
        this.taskNote = taskNote;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.category = category;
        this.favorite = favorite;
        this.Archived = isArchived;


    }

    public boolean isArchived() {
        return Archived;
    }

    public void setArchived(boolean archived) {
        Archived = archived;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskNote() {
        return taskNote;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public String toString() {
        return "Task{" +
                ", taskName='" + taskName + '\'' +
                ", category=" + category +
                ", user=" + user +
                ", isArchived=" + Archived +
                '}';
    }
}

