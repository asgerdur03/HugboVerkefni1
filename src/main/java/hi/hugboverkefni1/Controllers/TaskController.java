package hi.hugboverkefni1.Controllers;

import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.TaskPriority;
import hi.hugboverkefni1.persistence.entities.TaskStatus;
import hi.hugboverkefni1.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Tasks dont save to users account, need to add that.

    //home page, adds all tasks to page
    // filters
    @RequestMapping("/home")
    public String home(@RequestParam(required = false) String priority,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate,
                       @RequestParam(required = false) Boolean favorites,
                       Model model) {

        List<Task> tasks = taskService.findAllTasks();
        model.addAttribute("statuses", TaskStatus.values());
        // Apply filters
        if (priority != null && !priority.isEmpty()) {
            tasks = tasks.stream().filter(task -> task.getPriority().toString().equals(priority)).collect(Collectors.toList());
        }
        if (status != null && !status.isEmpty()) {
            tasks = tasks.stream().filter(task -> task.getStatus().toString().equals(status)).collect(Collectors.toList());
        }
        if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            tasks = tasks.stream().filter(task -> task.getDueDate().isAfter(start) && task.getDueDate().isBefore(end)).collect(Collectors.toList());
        }

        if (favorites != null) {
            tasks = tasks.stream()
                    .filter(task -> task.isFavorite() == favorites)
                    .collect(Collectors.toList());
        }


        model.addAttribute("tasks", tasks);
        return "home";
    }

    // Show the new task form and add enums to the model
    @RequestMapping(value = "/home/newTask", method = RequestMethod.GET)
    public String newTaskForm(Model model) {
        model.addAttribute("task", new Task()); // Add an empty task object to bind form data
        model.addAttribute("taskStatuses", TaskStatus.values()); // Pass TaskStatus enum values to the form
        model.addAttribute("taskPriorities", TaskPriority.values()); // Pass TaskPriority enum values to the form
        return "newTask";
    }

    // Add task to database and redirect to the home page
    @RequestMapping(value = "/home/newTask", method = RequestMethod.POST)
    public String newTask(@ModelAttribute Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("taskStatuses", TaskStatus.values()); //
            model.addAttribute("taskPriorities", TaskPriority.values()); //
            return "newTask";
        }
        taskService.save(task);
        return "redirect:/home";
    }

    /*
    // get task from form
    @RequestMapping(value ="/home/newTask", method= RequestMethod.GET)
    public String newTaskForm(Task task) {
        return "newTask";
    }

    // add task to database, and home page
    @RequestMapping(value = "/home/newTask", method = RequestMethod.POST)
    public String newTask(Task task, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "newTask";
        }
        taskService.save(task);
        return "redirect:/home";
    }
     */

    // delete task
    @RequestMapping(value="/home/delete/{id}")
    public String deleteTask(@PathVariable("id") long id, Model model) {
        Task taskToDelete = taskService.findById(id);
        taskService.delete(taskToDelete);
        return "redirect:/home";
    }

    // edit task
    @RequestMapping(value="/home/editTask/{id}", method = RequestMethod.GET)
    public String editTaskForm(@PathVariable("id") long id, Model model) {
        Task taskToEdit = taskService.findById(id);

        model.addAttribute("task", taskToEdit);
        model.addAttribute("taskStatuses", TaskStatus.values());
        model.addAttribute("taskPriorities", TaskPriority.values());
        return "edittask";
    }

    // update task
    /*
    not working currently
     */


    // add task to favorites
    @PostMapping("/home/addToFavorites/{id}")
    public String addToFavorites(@PathVariable("id") long id, Model model) {
        taskService.addToFavorites(id);
        return "redirect:/home";
    }

    // remove task from favorites
    @PostMapping("/home/removeFromFavorites/{id}")
    public String removeFromFavorites(@PathVariable("id") long id, Model model) {
        taskService.removeFromFavorites(id);
        return "redirect:/home";
    }




}





