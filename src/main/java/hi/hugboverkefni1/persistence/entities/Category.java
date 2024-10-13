package hi.hugboverkefni1.persistence.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String categoryName;

    //@ManyToOne(fetch = FetchType.LAZY)
   // private List<Task> tasks = new ArrayList<Task>();


    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @ManyToOne(optional = false)
    private User users;

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }
}


