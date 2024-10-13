package hi.hugboverkefni1.persistence.respositories;

import hi.hugboverkefni1.persistence.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    Task save(Task task);
    void delete(Task task);

    List<Task> findAll();
    Task findById(long id);




    @Query("SELECT t FROM Task t WHERE " +
            "(t.priority = :priority OR :priority IS NULL) AND " +
            "(t.status = :status OR :status IS NULL) AND " +
            "(t.dueDate BETWEEN :startDate AND :endDate OR (:startDate IS NULL AND :endDate IS NULL))")
    List<Task> findFilteredTasks(@Param("priority") String priority, @Param("status") String status,
                                 @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);








}
