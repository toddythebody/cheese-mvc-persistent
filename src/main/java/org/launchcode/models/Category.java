package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private int id;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{3,15}$", message = "3 to 15 characters, no spaces")
    private String name;

    public Category() { }

    public Category(String name) {
        this.name = name;
    }

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Cheese> cheeses = new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }

    public void setCheeses(List<Cheese> cheeses) {
        this.cheeses = cheeses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
