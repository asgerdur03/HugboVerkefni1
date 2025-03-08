package hi.hugboverkefni1.Controllers;

import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // category page, has all category functions
    /*
    @GetMapping("/categories")
    public String newCategory(Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedInUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Category> categories = categoryService.getAllCategoriesByUser(loggedUser);
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category());
        return "myCategories";
    }*/

    // save category to the user
    @PostMapping("/categories")
    public String createCategory(@ModelAttribute("categories") Category category, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        category.setUser(loggedInUser);

        categoryService.save(category);
        return "redirect:/categories";
    }


    // delete category
    @RequestMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") long id) {
        Category category = categoryService.findById(id);
        categoryService.delete(category);
        return "redirect:/categories";
    }
}


