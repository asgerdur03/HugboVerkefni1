package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.persistence.respositories.UserRepository;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    //private UserRepository userRepository;

    /*
    @Autowired
    public UserServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    */
    @Autowired
    public UserServiceImplementation() {

    }

    @Override
    public User getUserByID(int id) {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User loginUser(User user) {
        return null;
    }

    @Override
    public User signUpUser(User user) {
        return null;
    }

    @Override
    public Boolean validateInput(User user) {
        return null;
    }

    @Override
    public Boolean userExists(User user) {
        return null;
    }
}
