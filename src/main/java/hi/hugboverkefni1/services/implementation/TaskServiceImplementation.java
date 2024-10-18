package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.TaskPriority;
import hi.hugboverkefni1.persistence.entities.TaskStatus;
import hi.hugboverkefni1.persistence.respositories.TaskRepository;
import hi.hugboverkefni1.persistence.respositories.UserRepository;
import hi.hugboverkefni1.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImplementation implements TaskService {

    public TaskRepository taskRepository;

    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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






}
