package hi.hugboverkefni1.persistence.entities;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {

    private long id;
    private String taskName;
    private String taskNote;

    private TaskStatus status;
    private TaskPriority priority;

    private LocalDate dueDate;

    private User user;
    private Category category;


    public Task() {

    }

    public Task(long id, String taskName, String taskNote, TaskStatus status, TaskPriority priority, LocalDate dueDate, User user, Category category) {
        this.id = id;
        this.taskName = taskName;
        this.taskNote = taskNote;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.user = user;
        this.category = category;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
