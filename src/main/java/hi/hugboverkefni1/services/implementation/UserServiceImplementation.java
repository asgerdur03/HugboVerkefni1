package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.persistence.respositories.UserRepository;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);

    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);

    }


    /*
    @Override
    public void addNewUser(User user) {
        Optional<User> userByGmail = userRepository.findUserByGmail(user.getGmail());
        if(userByGmail.isPresent()){
            throw new IllegalStateException("User with Gmail address " + user.getGmail() + " already exists");
        }
        System.out.println(user);
        userRepository.save(user);
    }*/



}
