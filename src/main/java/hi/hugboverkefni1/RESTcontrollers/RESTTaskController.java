package hi.hugboverkefni1.RESTcontrollers;

import hi.hugboverkefni1.config.JwtService;
import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.CategoryService;
import hi.hugboverkefni1.services.TaskService;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RESTTaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final JwtService jwtService;
    private final CategoryService categoryService;


    @Autowired
    public RESTTaskController(TaskService taskService, UserService userService, JwtService jwtService,CategoryService categoryService) {
        this.taskService = taskService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.categoryService = categoryService;
    }

    // virkar, todo: bæta við fleiri filterum
    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Boolean favorite
            // todo: add more params
    ) {
        if (userDetails == null)
            return ResponseEntity.status(401).body(Map.of("message", "User not logged in"));

        User user = userService.findUsername(userDetails.getUsername());

        List<Task> tasks = taskService.findByUserId(user.getId());
        /*
        if (name != null) tasks = tasks.stream().filter(t -> t.getName().contains(name)).toList();
        if (category != null) tasks = tasks.stream().filter(t -> t.getCategory().getName().equals(category)).toList();
        if (priority != null) tasks = tasks.stream().filter(t -> t.getPriority().equals(priority)).toList();
        */

        if (favorite != null) tasks = tasks.stream().filter(t -> t.isFavorite() == favorite).toList();




        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTaskById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        if (userDetails == null)
            return ResponseEntity.status(401).body(Map.of("message", "User not logged in"));

        Task task = taskService.findById(id);

        if (task == null)
            return ResponseEntity.status(401).body(Map.of("message", "Task not found"));
        return ResponseEntity.ok(task);
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(
            @RequestBody Task task,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String message = "POST /tasks";
        if (userDetails == null)
            return ResponseEntity.status(401).body(Map.of("message", "User not logged in"));

        User user = userService.findUsername(userDetails.getUsername());
        task.setUser(user);

        if (task.getCategoryId() != null) {
            Category category = categoryService.findById(task.getCategoryId());
            if (category == null) {
                return ResponseEntity.status(401).body(Map.of("message", "Category not found"));
            }
            task.setCategory(category);
        }

        Task saved = taskService.save(task);

        return ResponseEntity.ok(Map.of("message", message, "task", saved));
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable String id
    ){
        String message = "PATCH /tasks/{id}";
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(
            @PathVariable long id
    ){
        Task task = taskService.findById(id);
        String message = "DELETE /tasks/"+id;
        taskService.delete(task);

        return ResponseEntity.ok(message);
    }


    @PutMapping("/tasks/{id}/favorite")
    public ResponseEntity<?> addFavorite(
            @PathVariable long id
    ){
        taskService.addToFavorites(id);
        String message = "PUT /tasks/" + id + "/favorite";
        return ResponseEntity.ok(message);
    }

    @PutMapping("/tasks/{id}/unfavorite")
    public ResponseEntity<?> removeFavorite(
            @PathVariable long id
    ){
        taskService.removeFromFavorites(id);
        String message = "PUT /tasks/" + id + "/unfavorite";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/tasks/archives")
    public ResponseEntity<?> getArchives(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null)
            return ResponseEntity.status(401).body(Map.of("message", "User not logged in"));

        User user = userService.findUsername(userDetails.getUsername());
        List<Task> archivedTasks = taskService.findArchivedTasks(user.getId());
        return ResponseEntity.ok(archivedTasks);
    }


    @PutMapping("/tasks/{id}/archive")
    public ResponseEntity<?> addArchive(
            @PathVariable long id
    ){
        String message = "PUT /tasks/"+ id+ "/archive";
        taskService.archiveTask(id);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/tasks/{id}/unarchive")
    public ResponseEntity<?> removeArchive(
            @PathVariable long id
    ){
        String message = "PUT /tasks/"+ id+ "/unarchive";
        taskService.unarchiveTask(id);
        return ResponseEntity.ok(message);
    }

}
