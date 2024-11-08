package hi.hugboverkefni1.Controllers;

import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Web opening page, login form and link to signup screen
    @RequestMapping("/login")
    public String home(Model model) {
        List<User> allUsers = userService.getUsers();
        model.addAttribute("users", allUsers);
        model.addAttribute("loginUser", new User());
        return "login";
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
    @RequestMapping(value = "/login/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        User userToDelete = userService.findUserById(id);
        userService.deleteUser(userToDelete);
        return "redirect:/login";
    }


    @PostMapping("/login")
    public String login(ModelMap model, @RequestParam String username, @RequestParam String password, HttpSession session) {
        boolean isValidUser = userService.validateUser(username, password);

        if (!isValidUser) {
          // model.put("errorMessage", "Invalid username or password");
            //List<User> allUsers = userService.getUsers();
            //model.put("users", allUsers);
            return "login";
        }
        User loggedInUser = userService.findUsername(username);

        session.setAttribute("loggedInUser", loggedInUser);
        //model.put("user", loggedInUser);

        System.out.println(session.getAttribute("loggedInUser").toString());

        return "redirect:/";
    }

    // Handles login submission
    // weird, does not work correctly
    /*

    @RequestMapping(value = "/opening-page", method = RequestMethod.POST)
    public String handleLogin(ModelMap model, @RequestParam String username, @RequestParam String password, HttpSession session ) {
        boolean isValidUser = userService.validateUser(username, password);

        if (!isValidUser) {
            model.put("errorMessage", "Access Denied: Invalid Credentials");
            List<User> allUsers = userService.getUsers();
            model.put("users", allUsers);
            return "opening-page";
        }
        // Connects username to login
        User loggedInUser = userService.findUsername(username);

        session.setAttribute("loggedInUser", loggedInUser);
        model.put("user", loggedInUser);

        return "home";

    }*/

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("session invalidated");
        return "redirect:/";
    }



    // Show update username form
    @GetMapping("/update-username")
    public String showUpdateUsernameForm(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("errorMessage", "Please log in to update username");
            return "redirect:/";
        }
        model.addAttribute("user", loggedInUser);
        return "update-username";
    }

    // Handle username update
    @PostMapping("/update-username")
    public String updateUsername(@RequestParam("newUsername") String newUsername, HttpSession session, ModelMap model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.put("errorMessage", "Please log in to update username");
            return "redirect:/login";
        }

        // Check if new username already exists
        User existingUser = userService.findUsername(newUsername);
        if (existingUser != null) {
            model.put("errorMessage", "Username already exists");
            return "update-username";
        }

        // Update username and save the user
        loggedInUser.setUsername(newUsername);
        userService.saveUser(loggedInUser);

        return "redirect:/";  // Redirect back to home after successful update
    }
 // Update gmail
    @GetMapping("/update-gmail")
    public String showChangeGmailForm(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("errorMessage", "Please log in to update email");
            return "redirect:/";
        }
        model.addAttribute("user", loggedInUser);
        return "update-gmail";
    }

    // handle gmail update
    @PostMapping("/update-gmail")
    public String updateGmail(@RequestParam("newGmail") String newGmail, HttpSession session, ModelMap model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.put("errorMessage", "Please log in to update gmail");
            return "redirect:/login";
        }

        // Check if new gmail already exists
        User existingUser = userService.findGmail(newGmail);
        if (existingUser != null) {
            model.put("errorMessage", "Gmail is already in use");
            return "update-gmail";
        }

        // Update gmail and save the user
        loggedInUser.setGmail(newGmail);
        userService.saveUser(loggedInUser);

        return "redirect:/";  // Redirect back to home after successful update
    }

    //update password
    @GetMapping("/update-password")
    public String showChangePasswordForm(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.addAttribute("errorMessage", "Please log in to update password");
            return "redirect:/";
        }
        model.addAttribute("user", loggedInUser);
        return "update-password";
    }

    //handle password update
    @PostMapping("/update-password")
    public String updatePassword(@RequestParam("newPassword") String newPassword, HttpSession session, ModelMap model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            model.put("errorMessage", "Please log in to update password");
            return "redirect:/login";
        }


        // Update password and save the user
        loggedInUser.setPassword(newPassword);
        userService.saveUser(loggedInUser);

        return "redirect:/";  // Redirect back to home after successful update
    }




    @GetMapping("/settings")
    public String settings() {
        return "settings";

    }
    @GetMapping("/profile-pic-selection")
    public String showProfilePicSelection(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Gefur options af x myndum með nafnscheme pic1,pic2.....
        List<String> profilePics = List.of("profilePics/pic1.png", "profilePics/pic2.png", "profilePics/pic3.png");
        model.addAttribute("profilePics", profilePics);
        model.addAttribute("loggedInUser", loggedInUser);
        return "profile-pic-selection";
    }

    @PostMapping("/profile-pic")
    public String saveProfilePic(@RequestParam("profilePic") String profilePic, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        loggedInUser.setProfilePicture(profilePic);
        userService.saveUser(loggedInUser);

        session.setAttribute("loggedInUser", loggedInUser);
        return "redirect:/";
    }
}
