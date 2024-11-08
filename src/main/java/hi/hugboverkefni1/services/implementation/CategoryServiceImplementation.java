package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.persistence.respositories.CategoryRepository;
import hi.hugboverkefni1.persistence.respositories.UserRepository;
import hi.hugboverkefni1.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CategoryServiceImplementation(CategoryRepository categoryRepository,UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
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
        categoryRepository.delete(category);

    }

    @Override
    public List<Category> getAllCategoriesByUser(User user) {
        return categoryRepository.findByUser(user);
    }
}
