package hi.hugboverkefni1.persistence.entities;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private long id;
    private String categoryName;

   // private List<Task> tasks = new ArrayList<Task>();


    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}


