package hi.hugboverkefni1.services.implementation;

import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.persistence.respositories.UserRepository;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    //private UserRepository userRepository;

    /*
    @Autowired
    public UserServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    */
    private List<User> userRepository =new ArrayList<User>();
    private int id_counter=0;



    @Autowired
    public UserServiceImplementation() {
        // mock data
        userRepository.add(new User("john_doe", "password123", "john.doe@gmail.com"));
        userRepository.add(new User("jane_smith", "securePass456", "jane.smith@gmail.com"));
        userRepository.add(new User("alice_wonder", "wonderland2024", "alice.wonder@gmail.com"));
        userRepository.add(new User("bob_builder", "builderStrong77", "bob.builder@gmail.com"));
        userRepository.add(new User("charlie_brown", "peanuts42", "charlie.brown@gmail.com"));

        for (User user : userRepository) {
            user.setId(id_counter);
            id_counter++;
        }

    }

    @Override
    public User getUserByID(int id) {
        for(User user:userRepository){
            if(user.getId()==id){
                return user;
            }
        }
        return null;
    }

    @Override
    public User saveUser(User user) {
        userRepository.add(user);
        return null;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.remove(user);
    }

    @Override
    public User loginUser(User user) {
        if (userRepository.contains(user) && user.getPassword().equals(userRepository.get(0).getPassword())) {
            System.out.println("logged in");
            return user;
        }
        return null;
    }

    @Override
    public User signUpUser(String username, String password, String email) {
        userRepository.add(new User(username, password, email));
        System.out.println("signed up");
        return null;
    }

    @Override
    public Boolean validateInput(User user) {
        return null;
    }

    @Override
    public Boolean userExists(User user) {
        for (User user1 : userRepository) {
            if(user1.getId()==user.getId()){
                return true;
            }
        }
        return false;
    }
}
