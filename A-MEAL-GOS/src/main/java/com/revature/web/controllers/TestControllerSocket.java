package com.revature.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.AppUser;
import com.revature.models.Meal;
import com.revature.models.Restaurant;
import com.revature.models.Vote;
import com.revature.services.MealService;
import com.revature.services.RestaurantService;
import com.revature.services.UserService;
import com.revature.services.VoteService;
import com.revature.web.dtos.VoteDto;
import com.revature.web.dtos.VoteStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TestControllerSocket {

    private final SimpMessagingTemplate template;
    private MealService mealService;
    private ObjectMapper mapper;
    private VoteService voteService;
    private RestaurantService restaurantService;
    private UserService userService;

    @Autowired
    public TestControllerSocket(SimpMessagingTemplate template, ObjectMapper map, VoteService service, RestaurantService service1, MealService service2, UserService service3) {
        this.template = template;
        this.mealService = service2;
        this.mapper = map;
        this.voteService = service;
        this.restaurantService = service1;
        this.mealService = service2;
        this.userService = service3;
    }

    @MessageMapping("/send/vote-message")
    public void sendMessage(String voteDto) throws JsonProcessingException {
        VoteDto thisVote = mapper.readValue(voteDto, VoteDto.class);
        System.out.println(thisVote);
        Meal thisMeal = mealService.getMealById(thisVote.getMeal());
        Restaurant thisRestaurant = restaurantService.getRestaurantById(thisVote.getRestaurant());
        AppUser thisUser = userService.getUserById(thisVote.getUser());


        Vote newVote = new Vote(thisRestaurant, thisMeal, thisUser, thisVote.getVote());

        if (voteService.getNumberOfVotesCast(thisUser) < thisMeal.getNumVotes() ) {
            voteService.createVote(newVote);
            VoteStatusDTO voteStatus = new VoteStatusDTO(thisMeal.getId(), voteService.getNumberOfVotesCast(userService.getUserById(thisVote.getUser())), 0, 0);
            template.convertAndSend("/vote-message", voteStatus);
        } else {
            mealService.addToFinishedVoting(thisUser, thisMeal);
            VoteStatusDTO voteStatus;
            if (thisMeal.getUsersFinishedVoting().size() == thisMeal.getUsersInMeal().size()) {
                voteStatus = new VoteStatusDTO(thisMeal.getId(), voteService.getNumberOfVotesCast(userService.getUserById(thisVote.getUser())), 1, 1);
            } else {
                voteStatus = new VoteStatusDTO(thisMeal.getId(), voteService.getNumberOfVotesCast(userService.getUserById(thisVote.getUser())), 1, 0);
            }
            template.convertAndSend("/vote-message", voteStatus);

        }




    }

}
