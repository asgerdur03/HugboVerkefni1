package hi.hugboverkefni1.Controllers;

import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.persistence.respositories.TaskRepository;
import hi.hugboverkefni1.services.CategoryService;
import hi.hugboverkefni1.services.TaskService;
import hi.hugboverkefni1.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;
    private TaskService taskService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService, TaskService taskService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.taskService = taskService;
    }


    @GetMapping("/categories")
    public String newCategory(Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedInUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Category> categories = categoryService.getAllCategoriesByUser(loggedUser);  // laga í get all með user_id
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category());
        return "myCategories";
    }

    @PostMapping("/categories")
    public String createCategory(@ModelAttribute("categories") Category category, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        category.setUser(loggedInUser);

        categoryService.save(category);
        return "redirect:/categories";
    }



    @RequestMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") long id, Model model) {
        Category category = categoryService.findById(id);
        categoryService.delete(category);
        return "redirect:/categories";
    }
}


