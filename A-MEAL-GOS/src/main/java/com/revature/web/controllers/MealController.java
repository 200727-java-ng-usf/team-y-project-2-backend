package com.revature.web.controllers;

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
    @Secured(allowedRoles = {"Admin", "Manager"})
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
    public Meal createMeal(@RequestBody Meal meal) {
        Meal newMeal = new Meal();
        if (meal.getNumVotes() == 0) {
            newMeal = new Meal(meal.getNumVotes(), meal.getMealName(), meal.getRestaurants());
        } else {
            newMeal = new Meal(meal.getNumVotes(), meal.getMealName(), meal.getRestaurants());
        }
        return mealService.create(newMeal);
    }
}