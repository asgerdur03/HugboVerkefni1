package hi.hugboverkefni1.services;

import hi.hugboverkefni1.persistence.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    List<User> getUsers();
    User findUserById(long id);
    void saveUser(User user);
    void deleteUser(User user);
    User findUsername(String username);

    boolean validateUser(String username, String password);



}
