package hi.hugboverkefni1.Controllers;

import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/categories")
    public String category(Model model) {
        //Call a method in a Service Class
        List<Category> categories = categoryService.getAllCategories();
        //Add some data to the Model
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/categories/new")
    public String newCategory(Category category) {
        return "new";
    }

    @PostMapping("/categories/new")
    public String newCategory(Model model, Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        
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


