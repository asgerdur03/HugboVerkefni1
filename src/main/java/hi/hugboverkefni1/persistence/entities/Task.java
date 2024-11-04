package hi.hugboverkefni1.persistence.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


@Entity
@Table(name = "tasks")
public class Task implements List<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String taskName;
    private String taskNote;


    private TaskStatus status;
    private TaskPriority priority;

    private LocalDate dueDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;


    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean favorite= false;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isArchived=false;

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
        this.isArchived = isArchived;


    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
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

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Task> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Task task) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Task> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Task> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Task get(int index) {
        return null;
    }

    @Override
    public Task set(int index, Task element) {
        return null;
    }

    @Override
    public void add(int index, Task element) {

    }

    @Override
    public Task remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Task> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Task> listIterator(int index) {
        return null;
    }

    @Override
    public List<Task> subList(int fromIndex, int toIndex) {
        return List.of();
    }
}
