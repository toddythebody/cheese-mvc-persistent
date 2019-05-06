package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
