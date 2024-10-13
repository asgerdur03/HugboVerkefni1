package hi.hugboverkefni1.Controllers;


import hi.hugboverkefni1.persistence.entities.Task;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Wep opening page, opens to login and signup screen
    @RequestMapping("/")
    public String home(Model model) {
        List<User> allUsers = userService.getUsers();
        model.addAttribute("users", allUsers);
        return "opening-page";
    }

    @RequestMapping("/signup")
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
        userService.saveUser(user);
        return "redirect:/";
    }

    @RequestMapping(value="/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User userToDelete = userService.findUserById(id);
        userService.deleteUser(userToDelete);
        return "redirect:/";
    }








}

/*
Create account Verify log-in POST
Create account Update username PATCH
 */