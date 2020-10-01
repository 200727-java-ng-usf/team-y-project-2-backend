package com.revature.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.AppUser;
import com.revature.models.Meal;
import com.revature.models.Restaurant;

import com.revature.models.Vote;
import com.revature.services.MealService;
import com.revature.web.dtos.ResultDto;

import com.revature.services.MealService;
import com.revature.services.UserService;
import com.revature.web.dtos.Credentials;
import com.revature.web.dtos.Principal;

import com.revature.services.RestaurantService;


import com.revature.web.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import java.util.Optional;

import java.util.Set;


@RestController
@RequestMapping("/meals")
public class MealController {

    private MealService mealService;
    private UserService userService;
    private RestaurantService restaurantService;

    @Autowired
    public MealController(MealService service, UserService service1, RestaurantService restaurantService) {
        this.mealService = service;
        this.restaurantService = restaurantService;
        this.userService = service1;

    }

    // produces is good practice to include.
//    @Secured(allowedRoles = {"Admin", "Manager"})
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Meal> getAllMeals() {
//        return mealService.getAllMeals();
//    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal getMealById(@PathVariable int id) {
        return mealService.getMealById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int setMealById(@RequestBody Credentials creds, @PathVariable int id, HttpServletRequest req) {
        int mealId = id;
        HttpSession userSession = req.getSession();
        userSession.setAttribute("mealId", mealId);
        AppUser currentUser = userService.getUserByUsername(creds.getUsername());
        System.out.println(currentUser);
        Meal currentMeal = mealService.getMealById(mealId);


        mealService.addToMeal(currentUser, currentMeal);
        return mealId;

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


    @GetMapping(value = "/results/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDto getWinner(@PathVariable int id) throws JsonProcessingException {

//        ObjectMapper mapper = new ObjectMapper();
//        HttpSession userSession = req.getSession();
//        String mealIdJSON = (String) userSession.getAttribute("mealId");
//        Meal meal = mapper.readValue(mealIdJSON, Meal.class);
//        System.out.println(meal);



        return mealService.getWinningMeal(id);
    }



    @PostMapping(value = "/voted/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int setMealFinishedVoting(@RequestBody Credentials creds, @PathVariable int id, HttpServletRequest req) {
        int mealId = id;
        HttpSession userSession = req.getSession();
        userSession.setAttribute("mealId", mealId);
        AppUser currentUser = userService.getUserByUsername(creds.getUsername());
        System.out.println(currentUser);
        Meal currentMeal = mealService.getMealById(mealId);


        mealService.addToFinishedVoting(currentUser, currentMeal);
        return mealId;

    }

}
