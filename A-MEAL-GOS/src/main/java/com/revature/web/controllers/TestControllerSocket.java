package com.revature.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Meal;
import com.revature.services.MealService;
import com.revature.web.dtos.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TestControllerSocket {

    private final SimpMessagingTemplate template;
    private MealService mealService;
    private ObjectMapper mapper;

    @Autowired
    public TestControllerSocket(SimpMessagingTemplate template, MealService service, ObjectMapper map) {
        this.template = template;
        this.mealService = service;
        this.mapper = map;
    }

    @MessageMapping("/send/vote-message")
    public void sendMessage(String voteDto) throws JsonProcessingException {
        VoteDto thisVote = mapper.readValue(voteDto, VoteDto.class);
        //jackson mapper
//        Meal votesMeal = mealService.getMealById(voteDto.getMeal());
//        int votesTotal = votesMeal.getNumVotes();
//        int userVotes
//        if (voteDto.getVote() == 1) {
//            if (v)
//        }
        //to do, get vote and log it, send back vote status?
        System.out.println(thisVote);
        template.convertAndSend("/vote-message", voteDto);
    }

}
