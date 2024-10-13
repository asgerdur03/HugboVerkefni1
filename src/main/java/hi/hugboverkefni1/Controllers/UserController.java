package hi.hugboverkefni1.Controllers;


import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Wep opening page, opens to login  and a link to signup screen
    @RequestMapping("/")
    public String home(Model model) {
        List<User> allUsers = userService.getUsers();
        model.addAttribute("users", allUsers);

        model.addAttribute("loginUser", new User());
        return "opening-page";
    }

    // Add user from signup form
    @RequestMapping(value ="/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }


    // adds user to database?
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "opening-page";
        }
        User exists = userService.findUsername(user.getUsername());
        if(exists == null) {
            userService.saveUser(user);
        }
        return "redirect:/";
    }

    // Deletes user (modify to delete account?)
    @RequestMapping(value="/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User userToDelete = userService.findUserById(id);
        userService.deleteUser(userToDelete);
        return "redirect:/";
    }

    // Handle login submission from the root URL
    @RequestMapping("/user")
    public String login(){
        return "testpage";
    }

}
