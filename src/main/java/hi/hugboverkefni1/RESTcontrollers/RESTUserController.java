package hi.hugboverkefni1.RESTcontrollers;

import hi.hugboverkefni1.config.JwtService;
import hi.hugboverkefni1.persistence.entities.User;
import hi.hugboverkefni1.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.lang.annotation.Retention;
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

    // Virkar, skilar token
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestParam String username,
            @RequestParam String password){

        try{
            boolean isValidUser = userService.validateUser(username, password);
            if(!isValidUser){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "wrong username or password"));
            }
            User user = userService.findUsername(username);

            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("user", user, "token", token));

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }

    }


    // virkar, opin rn
    @GetMapping("/admin")
    public ResponseEntity<?> getAdminPage(HttpServletRequest request){

        try{
            List<User> users = userService.getUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("what", "wrong username or password"));
        }
    }


    // virkar
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password
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


    // virkar jej, breytir bara email og passwordi ekki username
    // Er örlg hægt, en nn ekki að leysa unique vesið á username
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {

            User user = userService.findUsername(userDetails.getUsername());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "user not logged in"));
            }

            if (email != null) {
                user.setGmail(email);
            }
            if (password != null) {
                user.setPassword(password);
            }
            return ResponseEntity.ok(Map.of("message", "user updated", "user", user));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id
    ){
        User userToBeDeleted = userService.findUserById(id);
        userService.deleteUser(userToBeDeleted);
        return ResponseEntity.ok(Map.of("message", "delete successful"));
    }

    @DeleteMapping("/delete/me")
    public ResponseEntity<?> deleteMe(
            @AuthenticationPrincipal UserDetails userdetails
    ){
        User user = userService.findUsername(userdetails.getUsername());
        userService.deleteUser(user);
        return ResponseEntity.ok(Map.of("message", "delete successful"));
    }

}

