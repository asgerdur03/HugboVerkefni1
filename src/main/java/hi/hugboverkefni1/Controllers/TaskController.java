package hi.hugboverkefni1.Controllers;

import hi.hugboverkefni1.persistence.entities.*;
import hi.hugboverkefni1.services.CategoryService;
import hi.hugboverkefni1.services.TaskService;
import hi.hugboverkefni1.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    private CategoryService categoryService;
    private TaskService taskService;
    private UserService userService;


    Category noCategory = new Category("No category");

    @Autowired
    public TaskController(TaskService taskService, UserService userService, CategoryService categoryService) {
        this.taskService = taskService;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    //home page, add all task by user_id
    // filters
    @RequestMapping("/")
    public String home(@RequestParam(required = false) String priority,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate,
                       @RequestParam(required = false) Boolean favorites,
                       @RequestParam(required = false) Long category,
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

        if (category != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getCategory() != null && task.getCategory().getId().equals(category))
                    .collect(Collectors.toList());
        }


        model.addAttribute("tasks", tasks);


        List<Category> userCategories = categoryService.getAllCategoriesByUser(loggedInUser);

        model.addAttribute("categoryNames", userCategories);


        return "home";
    }

    @RequestMapping("/sort-by-name")
    public String sortName(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        List<Task> tasks= taskService.getAllTasksAlphabeticalOrder(loggedInUser.getId());

        model.addAttribute("tasks", tasks);
        model.addAttribute("loggedInUser", loggedInUser);

        List<Category> userCategories = categoryService.getAllCategoriesByUser(loggedInUser);
        model.addAttribute("categoryNames", userCategories);
        return "home";
    }

    @RequestMapping("/sort-by-date")
    public String sortDate(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        List<Task> tasks= taskService.getAllTasksInDateOrder(loggedInUser.getId());

        model.addAttribute("tasks", tasks);
        model.addAttribute("loggedInUser", loggedInUser);

        List<Category> userCategories = categoryService.getAllCategoriesByUser(loggedInUser);
        model.addAttribute("categoryNames", userCategories);
        return "home";
    }

    @RequestMapping("/sort-by-category")
    public String sortCategory(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        List<Task> tasks= taskService.getAllTasksInCategoriesOrder(loggedInUser.getId());

        model.addAttribute("tasks", tasks);
        model.addAttribute("loggedInUser", loggedInUser);

        List<Category> userCategories = categoryService.getAllCategoriesByUser(loggedInUser);
        model.addAttribute("categoryNames", userCategories);
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


        List<Category> userCategories = categoryService.getAllCategoriesByUser(user);
        userCategories.add(noCategory);

        model.addAttribute("categoryNames", userCategories);

        return "newTask";
    }

    // Add task to database and redirect to the home page
    @PostMapping("/newTask")
    public String saveTask(@ModelAttribute Task task, BindingResult result, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        if (result.hasErrors()) {
            model.addAttribute("taskStatuses", TaskStatus.values()); //
            model.addAttribute("taskPriorities", TaskPriority.values()); //
            List<Category> userCategories = categoryService.getAllCategoriesByUser(user);
            userCategories.add(noCategory);

            model.addAttribute("categoryNames", userCategories);

            //return "newTask";
            System.out.println(result.getAllErrors());
            return "newTask";
        }

        Long categoryId = task.getCategoryId();
        Category category = categoryService.findById(categoryId);

        task.setCategory(category);
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
    public String editTaskForm(@PathVariable("id") long id, Model model, HttpSession session) {
        Task taskToEdit = taskService.findById(id);
        User user = (User) session.getAttribute("loggedInUser");

        model.addAttribute("task", taskToEdit);
        model.addAttribute("taskStatuses", TaskStatus.values());
        model.addAttribute("taskPriorities", TaskPriority.values());

        List<Category> userCategories = categoryService.getAllCategoriesByUser(user);
        userCategories.add(noCategory);
        model.addAttribute("categoryNames", userCategories);

        taskToEdit.setCategoryId(taskToEdit.getCategory() != null ? taskToEdit.getCategory().getId() : null);

        return "edittask";
    }

    // update task
    @RequestMapping(value="/editTask/{id}", method = RequestMethod.POST)
    public String updateTask(@PathVariable("id") long id, @ModelAttribute Task updatedTask, BindingResult result, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (result.hasErrors()) {
            model.addAttribute("taskStatuses", TaskStatus.values());
            model.addAttribute("taskPriorities", TaskPriority.values());
            List<Category> userCategories = categoryService.getAllCategoriesByUser(user);
            userCategories.add(noCategory);
            model.addAttribute("categoryNames", userCategories);
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


        Long categoryId = updatedTask.getCategoryId();
        if (categoryId != null) {
            Category category = categoryService.findById(categoryId);
            existingTask.setCategory(category);
        } else {
            existingTask.setCategory(null); // Clear category if no category is selected
        }



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

    @Controller
    public class PomodoroController {

        @GetMapping("/pomodoro")
        public String pomodoro() {
            return "pomodoro";
        }
    }


}





