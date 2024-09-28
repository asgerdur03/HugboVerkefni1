package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.TaskPriority;
import hi.hugboverkefni1.persistence.entities.TaskStatus;
import hi.hugboverkefni1.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImplementation implements TaskService {
    //standinn for database
    private List<Task> taskRepository = new ArrayList<Task>();
    private int id_counter=0;

    @Autowired
    public TaskServiceImplementation() {
        //random tasks
        taskRepository.add(new Task("task1", "note1"));
        taskRepository.add(new Task("task2", "note2"));
        taskRepository.add(new Task("task3", "note3"));
        taskRepository.add(new Task("task4", "note4"));

        for(Task task : taskRepository) {
            task.setId(id_counter);
            id_counter++;
        }

    }

    @Override
    public Task findByTitle(String taskname) {
        for(Task task : taskRepository) {
            if(task.getTaskName().equals(taskname)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository;
    }

    @Override
    public Task findById(long id) {
        for(Task task : taskRepository) {
            if(task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    @Override
    public Task save(Task task) {
        taskRepository.add(task);
        return null;
    }

    @Override
    public void delete(Task task) {
        taskRepository.remove(task);

    }
}
