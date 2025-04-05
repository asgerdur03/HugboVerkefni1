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
import org.springframework.web.multipart.MultipartFile;


import java.lang.annotation.Retention;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public ResponseEntity<?> getAdminPage(){
        try{
            List<User> users = userService.getUsers();
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("users", users));
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "wrong username or password"));
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
            return ResponseEntity.ok(Map.of("message", "success", "user", newUser));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }


    // virkar jej, breytir bara email og passwordi ekki username
    // Er örlg hægt, en nn ekki að leysa unique vesið á username
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {

            User user = userService.findUsername(userDetails.getUsername()); // logged in user

            if (user == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "user not logged in"));}

            if (username != null && !username.isEmpty()) {
                User exists = userService.findUsername(username);
                if (exists != null && exists.getId() != user.getId()) { return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "username already exists"));}
                else{ user.setUsername(username);}
            }
            if (email != null && !email.isBlank() ) {user.setGmail(email);}
            if (password != null && !password.isBlank() ) {user.setPassword(password);}

            userService.saveUser(user);

            return ResponseEntity.ok(Map.of("user", user));

        } catch (Exception e) { return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage())); }
    }

    @PostMapping("/upload-pic")
    public ResponseEntity<?> uploadPicture(
            @RequestParam("image") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        if (userDetails == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing user or file"));
        }

        User user = userService.findUsername(userDetails.getUsername());



        try {
            String uploadDir = "src/main/resources/static/uploads/";
            Files.createDirectories(Paths.get(uploadDir));
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + filename);

            System.out.println(filePath);
            Files.copy(file.getInputStream(), filePath);

            user.setProfilePicture("/uploads/" + filename);
            userService.saveUser(user);

            return ResponseEntity.ok(Map.of("message", "Profile picture updated", "path", "/uploads/" + filename));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to save file"));
        }

    }


    // virkar
    @DeleteMapping("/delete/me")
    public ResponseEntity<?> deleteMe(
            @AuthenticationPrincipal UserDetails userdetails
    ){
        try{
            User user = userService.findUsername(userdetails.getUsername());
            if(user != null){
                userService.deleteUser(user);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }

        return ResponseEntity.ok(Map.of("message", "delete successful"));
    }

}

