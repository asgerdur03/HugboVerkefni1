package hi.hugboverkefni1.RESTcontrollers;

import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.TaskService;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RESTTaskController {

    private final TaskService taskService;
    private final UserService userService;


    @Autowired
    public RESTTaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null)
            return ResponseEntity.status(401).body(Map.of("message", "User not logged in"));

        User user = userService.findUsername(userDetails.getUsername());

        List<Task> tasks = taskService.findByUserId(user.getId());

        return ResponseEntity.ok(tasks);
    }


}
