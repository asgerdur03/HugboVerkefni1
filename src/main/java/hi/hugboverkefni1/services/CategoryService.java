package hi.hugboverkefni1.services;

import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAllCategories();

    Category createCategoryForUser(Long id, String name);
    void deleteCategory(Long id);
    Category findById(Long id);

    Category save(Category category);
    void delete(Category category);

    List<Category> getAllCategoriesByUser(User uses);



}
