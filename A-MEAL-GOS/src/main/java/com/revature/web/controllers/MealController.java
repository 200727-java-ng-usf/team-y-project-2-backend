package com.revature.web.controllers;

<<<<<<< HEAD
public class MealController {
}
=======
import com.revature.models.Meal;
import com.revature.services.MealService;
import com.revature.web.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

    private MealService mealService;

    @Autowired
    public MealController(MealService service) {
        this.mealService = service;
    }

    // produces is good practice to include.
//    @Secured(allowedRoles = {"Admin", "Manager"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal getMealById(@PathVariable int id) {
        return mealService.getMealById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int createMeal(@RequestBody Meal meal) {
        Meal newMeal = new Meal();

        if (meal.getNumVotes() == 0) {
            newMeal = new Meal(meal.getNumVotes(), meal.getMealName());
        } else {
            newMeal = new Meal(3, meal.getMealName());
        }
        mealService.create(newMeal);

        return newMeal.getId();
    }

}
>>>>>>> 0800ca30217832ca6068d399905ec4bdacf32b94
