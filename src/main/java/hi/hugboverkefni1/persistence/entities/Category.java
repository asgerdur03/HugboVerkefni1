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

    @ManyToOne(optional = true) //(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(unique = true, nullable = false)
    private String categoryName;

    private String color;

    @OneToMany(mappedBy = "category", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();



    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(User user, String categoryName, String color) {
        this.user = user;
        this.categoryName = categoryName;
        this.color = color;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return categoryName;
    }



}


