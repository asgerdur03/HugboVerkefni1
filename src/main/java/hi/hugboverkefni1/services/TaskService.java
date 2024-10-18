package hi.hugboverkefni1.services;


import hi.hugboverkefni1.persistence.entities.Task;

import java.time.LocalDate;
import java.util.List;


public interface TaskService {
    Task findByTitle(String title);
    //Task findByDate(LocalDate date);
    //Task findByPriority( priority);

    List<Task> findAllTasks();

    Task findById(long id);

    Task save(Task task);

    void delete(Task task);

    void addToFavorites(long id);
    void removeFromFavorites(long id);

    List<Task> findFilteredTasks(String priority, String status, LocalDate startDate, LocalDate endDate);

    List<Task> findActiveTasks();
    List<Task> findArchivedTasks();
    void archiveTask(long id);
    void unarchiveTask(long id);
}



