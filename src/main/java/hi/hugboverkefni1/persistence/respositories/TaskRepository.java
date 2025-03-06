package hi.hugboverkefni1.persistence.respositories;

import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.User;
import org.springframework.data.domain.Sort;
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


    @Query("select t from Task t where t.user.id = :userId and t.Archived = false")
    List<Task> findUnarchivedTasks(@Param("userId") long userId);

    @Query("select t from Task t where t.user.id = :userId and t.Archived = true")
    List<Task> findArchivedTasks(@Param("userId") long userId);


    List<Task> findByCategory(Category category);


    @Query("SELECT t from Task t where t.user.id = :userId and t.Archived=false Order by t.taskName asc ")
    List<Task> GetAllTasksAlphabeticalOrder(@Param("userId") long userId);

    @Query("select t from Task t where t.user.id = :userId and t.Archived=false Order by t.dueDate ")
    List<Task> GetAllTasksDateOrder(@Param("userId") long userId);

    @Query("select t from Task t where t.user.id = :userId order by t.category.categoryName ")
    List<Task> GetAllTasksCategoryOrder(@Param("userId") long userId);


    List<Task> findAll(Sort sort);

    List<Task> findByUserId(long userId);




    @Query("SELECT t FROM Task t WHERE " +
            "(t.priority = :priority OR :priority IS NULL) AND " +
            "(t.status = :status OR :status IS NULL) AND " +
            "(t.dueDate BETWEEN :startDate AND :endDate OR (:startDate IS NULL AND :endDate IS NULL))")
    List<Task> findFilteredTasks(@Param("priority") String priority, @Param("status") String status,
                                 @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



}
