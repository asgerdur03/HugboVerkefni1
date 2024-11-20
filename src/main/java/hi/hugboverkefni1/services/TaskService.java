package hi.hugboverkefni1.services;


import hi.hugboverkefni1.persistence.entities.Task;

import java.time.LocalDate;
import java.util.List;


public interface TaskService {
    Task findById(long id);
    Task save(Task task);
    void delete(Task task);
    void addToFavorites(long id);
    void removeFromFavorites(long id);
    List<Task> findActiveTasks(long userId);
    List<Task> findArchivedTasks(long userid);
    void archiveTask(long id);
    void unarchiveTask(long id);
    List<Task> findByUserId(long userId);
    List<Task> getAllTasksAlphabeticalOrder(long userId);
    List<Task> getAllTasksInDateOrder(long userId);
    List<Task> getAllTasksInCategoriesOrder(long userId);


}



