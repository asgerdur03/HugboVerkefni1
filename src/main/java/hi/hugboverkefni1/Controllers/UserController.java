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
    public String signup(User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "signup";
        }
        User exists = userService.findUsername(user.getUsername());

        if (exists != null) {
            model.put("errorMessage", "Username taken, pick new one");

            System.out.println(exists.getUsername());
            return "signup";

        }
        userService.saveUser(user);
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
        // Connects username to login
        User loggedInUser = userService.findUsername(username);
        model.put("user", loggedInUser);

        return "home";

    }

    // Show update username form
    @GetMapping("/update-username/{id}")
    public String showUpdateUsernameForm(@PathVariable("id") long id, Model model) {
        User user = userService.findUserById(id);
        if (user == null) {
            model.addAttribute("errorMessage", "User not found");
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "user-page";
    }

    // Handle username update
    @PostMapping("/update-username")
    public String updateUsername(@RequestParam("id") long id, @RequestParam("newUsername") String newUsername, ModelMap model) {
        User user = userService.findUserById(id);
        if (user == null) {
            model.put("errorMessage", "User not found");
            return "redirect:/";
        }

        // Check if new username already exists
        User existingUser = userService.findUsername(newUsername);
        if (existingUser != null) {
            model.put("errorMessage", "Username already exists");
            return "user-page";
        }

        // Update username and save the user
        user.setUsername(newUsername);
        userService.saveUser(user);

        return "redirect:/home";  // Redirect back to home after successful update
    }
}
