package hi.hugboverkefni1.persistence.entities;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import java.util.*;


import java.awt.*;
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

    @OneToMany(mappedBy = "category", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<Task>();

    public Category() {
    }

    // to create category
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(User user, String categoryName) {
        this.user = user;
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


