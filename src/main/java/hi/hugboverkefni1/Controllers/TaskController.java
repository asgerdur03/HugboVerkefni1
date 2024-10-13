package hi.hugboverkefni1.Controllers;

import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //home page
    @RequestMapping("/home")
    public String home(Model model) {

        List<Task> allTasks = taskService.findAllTasks();
        model.addAttribute("tasks", allTasks);
        return "home";
    }

    @RequestMapping(value ="/home/newTask", method= RequestMethod.GET)
    public String newTaskForm(Task task) {

        return "newTask";
    }

    @RequestMapping(value = "/home/newTask", method = RequestMethod.POST)
    public String newTask(Task task, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "newTask";
        }
        taskService.save(task);
        return "redirect:/home";
    }

    @RequestMapping(value="/home/delete/{id}")
    public String deleteTask(@PathVariable("id") long id, Model model) {
        Task taskToDelete = taskService.findById(id);
        taskService.delete(taskToDelete);
        return "redirect:/home";
    }





}
