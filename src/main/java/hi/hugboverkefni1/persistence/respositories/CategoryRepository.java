package hi.hugboverkefni1.persistence.respositories;

import hi.hugboverkefni1.persistence.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
