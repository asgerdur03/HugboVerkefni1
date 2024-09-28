package hi.hugboverkefni1.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

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