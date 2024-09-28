package hi.hugboverkefni1.persistence.entities;

import jakarta.persistence.*;
import org.springframework.scheduling.config.Task;
import java.util.*;


import java.awt.*;
import java.util.List;

//@Entity
//@Table(name="users")
public class User {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    private String gmail;

    //@(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //List<Task> tasks = new ArrayList<>();
    //private List<Category> categories = new ArrayList<>();

    private String profilePicture; //url

    public User() {

    }

    public User(String username, String password, String gmail) {
        this.username = username;
        this.password = password;
        this.gmail = gmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
