package hi.hugboverkefni1.services;

import hi.hugboverkefni1.persistence.entities.User;
import java.util.List;

public interface UserService {

    List<User> getUsers();
    User findUserById(long id);
    void saveUser(User user);
    void deleteUser(User user);
    User findUsername(String username);

    User validateUser(String username, String password);


}
