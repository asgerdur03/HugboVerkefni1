package hi.hugboverkefni1.Controllers;


import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/")
    public String UserController() {
        //busines logic
        //call a methodd in a service class
        // add some data to the model
        return "opening-page"; //leitar að html file clalled opening-page
    }

    @RequestMapping("/signup")
    public String signupPage() {
        return "signup";
    }



}

/*
Create account Verify log-in POST
Create account Update username PATCH
 */