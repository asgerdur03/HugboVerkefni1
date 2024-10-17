package hi.hugboverkefni1.Controllers;

import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

    // Web opening page, login form and link to signup screen
    @RequestMapping("/")
    public String home(Model model) {
        List<User> allUsers = userService.getUsers();
        model.addAttribute("users", allUsers);
        model.addAttribute("loginUser", new User());
        return "opening-page";
    }

    // Add user from signup form
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Add user to the database
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }
        User exists = userService.findUsername(user.getUsername());
        if (exists == null) {
            userService.saveUser(user);
        }
        return "redirect:/";
    }

    // Delete user
    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        User userToDelete = userService.findUserById(id);
        userService.deleteUser(userToDelete);
        return "redirect:/";
    }

    // Handles login submission
    @RequestMapping(value = "/opening-page", method = RequestMethod.POST)
    public String handleLogin(ModelMap model, @RequestParam String username, @RequestParam String password) {
        boolean isValidUser = userService.validateUser(username, password);

        if (!isValidUser) {
            model.put("errorMessage", "Access Denied: Invalid Credentials");
            List<User> allUsers = userService.getUsers();
            model.put("users", allUsers);
            return "opening-page";
        }

        // If the login is successful, redirect to the home page
        model.put("username", username);
        return "home";
    }
}
