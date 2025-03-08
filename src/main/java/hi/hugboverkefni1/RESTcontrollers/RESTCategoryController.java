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

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        //User loggedUser = (User) httpsession.getAttribute("loggedUser");

        // temp, skipta ut fyrir validation
        User loggedUser = userService.findUserById(1);

        System.out.println(loggedUser);

        if (loggedUser == null) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "no logged in user"));
        }

        List<Category> categories = categoryService.getAllCategoriesByUser(loggedUser);

        System.out.println(categories);
        return new ResponseEntity<>(categories, HttpStatus.OK);

    }








}
