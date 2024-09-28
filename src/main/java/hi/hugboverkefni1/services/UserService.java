package hi.hugboverkefni1.services;

import hi.hugboverkefni1.persistence.entities.User;

public interface UserService {
    User getUserByID(int id);

    User saveUser(User user);
    void deleteUser(User user);

    User loginUser(User user);
    User signUpUser(User user);

    Boolean validateInput(User user);
    Boolean userExists(User user);


}
