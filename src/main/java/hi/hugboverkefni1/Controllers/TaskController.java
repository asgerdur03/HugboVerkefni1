package hi.hugboverkefni1.Controllers;

import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.TaskPriority;
import hi.hugboverkefni1.persistence.entities.TaskStatus;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.TaskService;
import hi.hugboverkefni1.services.UserService;
import jakarta.servlet.http.HttpSession;
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

    private TaskService taskService;
    private UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }


    //home page, add all task by user_id
    // filters
    @RequestMapping("/")
    public String home(@RequestParam(required = false) String priority,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate,
                       @RequestParam(required = false) Boolean favorites,
                       Model model,
                       HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");


        if (loggedInUser == null) {
            return "redirect:/login";
        }


        List<Task> activeTasks= taskService.findActiveTasks();
        // get all tasks with that user id
        List<Task> tasks = taskService.findByUserId(loggedInUser.getId());

        //tasks á báðum listum
        tasks.retainAll(activeTasks);

        model.addAttribute("tasks", tasks);

        model.addAttribute("loggedInUser", loggedInUser);


        //List<Task> tasks = taskService.findActiveTasks();
        //model.addAttribute("statuses", TaskStatus.values());
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
    @GetMapping("/newTask")
    public String newTaskForm(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("task", new Task()); // Add an empty task object to bind form data
        model.addAttribute("taskStatuses", TaskStatus.values()); // Pass TaskStatus enum values to the form
        model.addAttribute("taskPriorities", TaskPriority.values()); // Pass TaskPriority enum values to the form

        return "newTask";
    }

    // Add task to database and redirect to the home page
    //@RequestMapping(value = "/home/newTask", method = RequestMethod.POST)

    @PostMapping("/newTask")
    public String newTask(@ModelAttribute Task task, BindingResult result, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        if (result.hasErrors()) {
            model.addAttribute("taskStatuses", TaskStatus.values()); //
            model.addAttribute("taskPriorities", TaskPriority.values()); //
            return "newTask";
        }
        task.setUser(user);
        //task.setUserId(user.getId());
        taskService.save(task);
        return "redirect:/";
    }


    // delete task
    @RequestMapping(value="/delete/{id}")
    public String deleteTask(@PathVariable("id") long id) {
        Task taskToDelete = taskService.findById(id);
        taskService.delete(taskToDelete);
        return "redirect:/";
    }

    // edit task
    // edit the task attributes
    @RequestMapping(value="/editTask/{id}", method = RequestMethod.GET)
    public String editTaskForm(@PathVariable("id") long id, Model model) {
        Task taskToEdit = taskService.findById(id);

        model.addAttribute("task", taskToEdit);
        model.addAttribute("taskStatuses", TaskStatus.values());
        model.addAttribute("taskPriorities", TaskPriority.values());
        return "edittask";
    }

    // update task
    @RequestMapping(value="/editTask/{id}", method = RequestMethod.POST)
    public String updateTask(@PathVariable("id") long id, @ModelAttribute Task updatedTask, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("taskStatuses", TaskStatus.values());
            model.addAttribute("taskPriorities", TaskPriority.values());
            return "edittask";
        }

        // finna task með id
        Task existingTask = taskService.findById(id);
        // updatea með nýju infoi
        existingTask.setTaskName(updatedTask.getTaskName());
        existingTask.setTaskNote(updatedTask.getTaskNote());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setDueDate(updatedTask.getDueDate());


        taskService.save(existingTask);
        return "redirect:/";
    }

    // add task to favorites
    @PostMapping("/addToFavorites/{id}")
    public String addToFavorites(@PathVariable("id") long id, Model model) {
        taskService.addToFavorites(id);
        return "redirect:/";
    }

    // remove task from favorites
    @PostMapping("/removeFromFavorites/{id}")
    public String removeFromFavorites(@PathVariable("id") long id, Model model) {
        taskService.removeFromFavorites(id);
        return "redirect:/";
    }

    // birta archived page
    // er ekki að birta archived tasks
    @GetMapping("/archive")
    public String archivedTasks(Model model,  HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        List<Task> userTask = taskService.findByUserId(user.getId());
        List<Task> archivedTasks = taskService.findArchivedTasks();

        archivedTasks.retainAll(userTask);
        model.addAttribute("tasks", archivedTasks);
        return "archived";
    }

    // Archive a task
    @PostMapping("/archive/{id}")
    public String archiveTask(@PathVariable("id") long id) {
        taskService.archiveTask(id);
        return "redirect:/";
    }

    // Unarchive a task
    @PostMapping("/archive/unarchived/{id}")
    public String unarchiveTask(@PathVariable("id") long id) {
        System.out.println("unarchive task: " + id);
        taskService.unarchiveTask(id);
        return "redirect:/archive";
    }



}





