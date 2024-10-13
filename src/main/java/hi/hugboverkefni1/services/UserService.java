package hi.hugboverkefni1.services;

import hi.hugboverkefni1.persistence.entities.User;
import java.util.List;

public interface UserService {

    List<User> getUsers();
    User findUserById(int id);
    void saveUser(User user);
    void deleteUser(User user);

}
