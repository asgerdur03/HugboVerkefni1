package hi.hugboverkefni1.RESTcontrollers;

import hi.hugboverkefni1.config.JwtService;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
public class RESTUserController {

    private final JwtService jwtService;
    private UserService userService;

    @Autowired
    private RESTUserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request){

        try{
            boolean isValidUser = userService.validateUser(username, password);
            if(!isValidUser){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "wrong username or password"));
            }
            User user = userService.findUsername(username);

            String token = jwtService.generateToken(user.getUsername());

            /*
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
*/
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("user", user, "token", token));

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }

    }


    @GetMapping("/admin")
    public ResponseEntity<?> getAdminPage(HttpServletRequest request){

        try{
            List<User> users = userService.getUsers();
            System.out.print(users);

            return ResponseEntity.status(HttpStatus.OK).body(users);

        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("what", "wrong username or password"));

        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request
    ){
        try{
            User taken = userService.findUsername(username);
            if(taken != null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "username already taken"));
            }
            User newUser = new User(username, password, email);
            userService.saveUser(newUser);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
        return ResponseEntity.ok("signup successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("logout successful");
    }


    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            HttpServletRequest request
    ) {
        try {

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "user not logged in"));
            }

            if (email != null) {
                user.setGmail(email);
            }
            if (password != null) {
                user.setPassword(password);
            }
            userService.saveUser(user);

            request.getSession().setAttribute("loggedInUser", user);

            return ResponseEntity.ok(Map.of("message", "user updated", "user", user));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

}

