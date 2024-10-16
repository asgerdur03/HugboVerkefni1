package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.persistence.respositories.UserRepository;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User findUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findUserById(long id) {

        return userRepository.findById(id);
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
    public User findUsername(String username) {
        return userRepository.findByUsername(username);
    } */
    /*

    @Override
    public User validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user.getPassword().equals(password) && user != null) {
            return user;
        }
        return null;
    }*/

    /* validate user boolean í vinnslu'/

     */ /*
    public boolean validateUser(String username, String password){
        return username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("password");
    }  Version 2 , virkar ekki*/

    @Override
    public boolean validateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            System.out.println("User not found: " + username);
            return false;
        }


        if (user.get().getPassword().equals(password)) {
            System.out.println("User validated successfully: " + username);
            return true;
        } else {
            System.out.println("Invalid password for user: " + username);
            return false;
        }
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
