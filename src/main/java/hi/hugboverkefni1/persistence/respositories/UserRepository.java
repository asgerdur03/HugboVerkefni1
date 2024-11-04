package hi.hugboverkefni1.persistence.respositories;

import hi.hugboverkefni1.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);
    User save(User user);
    void delete(User user);

    List<User> findAll();
    User findById(long id);

    /*User findByUsername(String username);*/




}
