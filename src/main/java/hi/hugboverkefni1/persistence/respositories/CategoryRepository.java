package hi.hugboverkefni1.persistence.respositories;

import hi.hugboverkefni1.persistence.entities.Category;
import hi.hugboverkefni1.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    Category findById(long id);

    Category save(Category category);
    void delete(Category category);


}
