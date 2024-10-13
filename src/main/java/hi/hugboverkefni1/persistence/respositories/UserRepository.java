package hi.hugboverkefni1.persistence.respositories;

import hi.hugboverkefni1.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);
    void delete(User user);

    List<User> findAll();
    User findById(long id);

    User findByUsername(String username);


}
