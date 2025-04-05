package hi.hugboverkefni1.RESTcontrollers;


import hi.hugboverkefni1.config.JwtService;
import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.CategoryService;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

        if (user == null) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "user with this username"));
        }

        List<Category> categories = categoryService.getAllCategoriesByUser(user);

        return ResponseEntity.ok(Map.of("categories", categories));

    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategory(
            @PathVariable long id,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        if (userDetails == null) {
            return ResponseEntity.status(401).body(Map.of("message", "User not logged in"));
        }
        String message = "GET /categories/ " + id ;

        Category category = categoryService.findById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "no category with id: "+ id));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("category", category));
    }

    @PostMapping("/categories")
    public ResponseEntity<?> addCategory(
            @RequestBody Category category,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        User user = userService.findUsername(userDetails.getUsername());
        category.setUser(user);

        Category saved = categoryService.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("category", saved));
    }

    @PatchMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable long id,
            @RequestBody Category updated,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        User user = userService.findUsername(userDetails.getUsername());
        Category category = categoryService.findById(id);

        if (category == null || !category.getUser().getUsername().equals(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "no category with id: "+ id));
        }
        if (updated.getCategoryName() != null) {
            category.setCategoryName(updated.getCategoryName());
        }
        if (updated.getColor() != null) {
            category.setColor(updated.getColor());
        }

        categoryService.save(category);

        return ResponseEntity.ok(Map.of("category", category));


    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable long id,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        String message = "DELETE //categories/" + id ;
        Category category = categoryService.findById(id);
        categoryService.delete(category);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }









}
