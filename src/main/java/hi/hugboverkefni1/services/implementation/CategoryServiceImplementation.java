package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.Controllers.CategoryController;
import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.persistence.respositories.CategoryRepository;
import hi.hugboverkefni1.persistence.respositories.TaskRepository;
import hi.hugboverkefni1.persistence.respositories.UserRepository;
import hi.hugboverkefni1.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public CategoryServiceImplementation(CategoryRepository categoryRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategoryForUser(Long id, String name) {
        User user = userRepository.findById(id).get();
        Category category = new Category();
        category.setCategoryName(name);
        category.setUser(user);
        return null;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }


    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        List<Task> tasksInCategory = taskRepository.findByCategory(category);
        if (tasksInCategory.isEmpty()) {
            categoryRepository.delete(category);
        } else {
            System.out.println("Category in use");
        }
    }

    @Override
    public List<Category> getAllCategoriesByUser(User user) {
        return categoryRepository.findByUser(user);
    }
}
