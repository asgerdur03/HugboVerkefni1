package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.respositories.CategoryRepository;
import hi.hugboverkefni1.persistence.respositories.TaskRepository;
import hi.hugboverkefni1.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImplementation implements TaskService {

    private TaskRepository taskRepository;
    private CategoryRepository categoryRepository;


    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
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
    public Task findByTitle(String title) {
        return null;
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
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

    public List<Task> findFilteredTasks(String priority, String status, LocalDate startDate, LocalDate endDate) {
        return taskRepository.findFilteredTasks(priority, status, startDate, endDate);
    }

    @Override
    public List<Task> findActiveTasks() {
        return taskRepository.findByIsArchivedIsFalse();
    }

    @Override
    public List<Task> findArchivedTasks() {
        return taskRepository.findByIsArchivedIsTrue();
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
    public void assignTaskToCategory(long taskId, long categoryId) {
        Task task = taskRepository.findById(taskId);
        Category category = categoryRepository.findById(categoryId);
        task.setCategory(category);
        taskRepository.save(task);


    }




}
