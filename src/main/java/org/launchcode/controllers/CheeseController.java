package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private CategoryDao categoryDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute @Valid Cheese newCheese,
                                       Errors errors, Model model, @RequestParam int categoryId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            return "cheese/add";
        }
        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);
        cheeseDao.save(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam(value = "cheeseIds", required = false, defaultValue = "") int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "category/{categoryId}")
    public String category(Model model, @PathVariable int categoryId) {
        List<Cheese> cheeses = categoryDao.findOne(categoryId).getCheeses();
        model.addAttribute("cheeses", cheeses);
        model.addAttribute("title", "Cheeses with type: " + categoryDao.findOne(categoryId).getName());
        return "cheese/index";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int cheeseId) {

        Cheese theCheese = cheeseDao.findOne(cheeseId);
        String title = "Editing " + theCheese.getName() + "(Id# " + theCheese.getId() + ")";

        model.addAttribute("title", title);
        model.addAttribute("theCheese", theCheese);
        model.addAttribute(theCheese);
        model.addAttribute("categories", categoryDao.findAll());

        return "cheese/edit";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditForm(@PathVariable int cheeseId,
                                  @ModelAttribute @Valid Cheese newCheese, Errors errors, Model model,
                                  @RequestParam int categoryId) {

        if (errors.hasErrors()) {
            String title = "Editing " + newCheese.getName() + "(Id# " + newCheese.getId() + ")";
            model.addAttribute("title", title);
            Cheese theCheese = cheeseDao.findOne(cheeseId);
            model.addAttribute("theCheese", theCheese);
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/edit";
        }

        Category cat = categoryDao.findOne(categoryId);
        Cheese theCheese = cheeseDao.findOne(cheeseId);
        theCheese.setName(newCheese.getName());
        theCheese.setDescription(newCheese.getDescription());
        theCheese.setCategory(cat);
        cheeseDao.save(theCheese);
        return "redirect:/cheese";
    }

}
