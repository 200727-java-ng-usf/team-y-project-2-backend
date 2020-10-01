package com.revature.web.controllers;

import com.revature.models.Meal;
import com.revature.models.Restaurant;
import com.revature.services.MealService;
import com.revature.services.RestaurantService;
import com.revature.web.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/meals")
public class MealController {

    private MealService mealService;
    private RestaurantService restaurantService;

    @Autowired
    public MealController(MealService service, RestaurantService restaurantService) {
        this.mealService = service;
        this.restaurantService = restaurantService;
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
            newMeal = new Meal(meal.getNumVotes(), meal.getMealName(), meal.getRestaurants());
        } else {
            newMeal = new Meal(3, meal.getMealName(), meal.getRestaurants());
        }

        restaurantService.createRestaurants((Set<Restaurant>) meal.getRestaurants());
        mealService.create(newMeal);

        return newMeal.getId();
    }

}