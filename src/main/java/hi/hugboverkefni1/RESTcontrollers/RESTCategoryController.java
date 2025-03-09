package hi.hugboverkefni1.RESTcontrollers;


import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.CategoryService;
import hi.hugboverkefni1.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RESTCategoryController {
    private UserService userService;
    private CategoryService categoryService;

    @Autowired
    public RESTCategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    // virkar
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body(Map.of("message", "User not logged in"));
        }

        User user = userService.findUsername(userDetails.getUsername());
       // User user = userService.findUserById(1);

        if (user == null) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "user with this username"));
        }

        List<Category> categories = categoryService.getAllCategoriesByUser(user);

        return ResponseEntity.ok(categories);

    }








}
