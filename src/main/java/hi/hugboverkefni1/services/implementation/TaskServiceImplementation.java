package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.respositories.CategoryRepository;
import hi.hugboverkefni1.persistence.respositories.TaskRepository;
import hi.hugboverkefni1.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImplementation implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findByUserId(long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public void addToFavorites(long id){
        Task task = taskRepository.findById(id);
        if(task != null){
            task.setFavorite(true);
            taskRepository.save(task);
        }
    }

    @Override
    public void removeFromFavorites(long id){
        Task task = taskRepository.findById(id);
        if(task != null){
            task.setFavorite(false);
            taskRepository.save(task);
        }
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public List<Task> findActiveTasks(long userId) {
        return taskRepository.findUnarchivedTasks(userId);
    }

    @Override
    public List<Task> findArchivedTasks(long userId) {
        return taskRepository.findArchivedTasks(userId);
    }

    @Override
    public void archiveTask(long id) {
        Task task = taskRepository.findById(id);
        if(task != null){
            task.setArchived(true);
            taskRepository.save(task);
        }
    }

    @Override
    public void unarchiveTask(long id) {
        Task task = taskRepository.findById(id);
        if(task != null){
            task.setArchived(false);
            taskRepository.save(task);
        }
    }

    @Override
    public List<Task> getAllTasksAlphabeticalOrder(long userId) {
        return taskRepository.GetAllTasksAlphabeticalOrder(userId);
    }
    @Override
    public List<Task> getAllTasksInDateOrder(long userId) {
        return taskRepository.GetAllTasksDateOrder(userId);
    }

    @Override
    public List<Task> getAllTasksInCategoriesOrder(long userId) {
        return taskRepository.GetAllTasksCategoryOrder(userId);
    }
}
