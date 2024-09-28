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

}
