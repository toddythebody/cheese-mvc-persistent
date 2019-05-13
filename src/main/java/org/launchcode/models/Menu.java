package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int id;
    @NotNull
    @Pattern(regexp = "^[\\w ']{3,15}$", message = "3 to 15 characters")
    private String name;
    @ManyToMany
    private List<Cheese> cheeses;

    public Menu() { }

    public Menu(String name) {
        this.name = name;
    }

    public void addItem(Cheese item) {
        cheeses.add(item);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }
}
