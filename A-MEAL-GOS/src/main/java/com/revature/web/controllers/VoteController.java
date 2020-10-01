package com.revature.web.controllers;

import com.revature.models.Restaurant;
import com.revature.models.Vote;
import com.revature.services.MealService;
import com.revature.services.RestaurantService;
import com.revature.services.UserService;
import com.revature.services.VoteService;
import com.revature.web.dtos.VoteDto;
import com.revature.web.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private VoteService voteService;
    private RestaurantService restaurantService;
    private MealService mealService;
    private UserService userService;

    @Autowired
    public VoteController(VoteService service, RestaurantService service1, MealService service2, UserService service3) {
        this.voteService = service;
        this.restaurantService = service1;
        this.mealService = service2;
        this.userService = service3;
    }

    // produces is good practice to include.
    @Secured(allowedRoles = {"Admin", "Manager"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAllVotes() {
        return voteService.getAllVotes();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote createVote(@RequestBody VoteDto vote) {
        System.out.println(vote.getVote());
        Vote newVote = new Vote(restaurantService.getRestaurantById(vote.getRestaurant()), mealService.getMealById(vote.getMeal()), userService.getUserById(vote.getUser()), vote.getVote());
        return voteService.createVote(newVote);
    }

}