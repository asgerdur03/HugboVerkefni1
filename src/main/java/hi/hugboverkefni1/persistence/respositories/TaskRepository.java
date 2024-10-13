package hi.hugboverkefni1.persistence.respositories;

import hi.hugboverkefni1.persistence.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    Task save(Task task);
    void delete(Task task);

    List<Task> findAll();








}
