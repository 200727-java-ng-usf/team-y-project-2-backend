package com.revature.web.controllers;

import com.revature.models.AppUser;
import com.revature.models.Restaurant;
import com.revature.services.RestaurantService;
import com.revature.services.UserService;
import com.revature.web.dtos.Credentials;
import com.revature.web.dtos.RestaurantDto;
import com.revature.web.dtos.UserVote;
import com.revature.web.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController
// 		implies @Controller at the class level
// 		@Responsebody on the return type of each mapping method.
@RestController
@RequestMapping("/users")
public class UserController {

	private RestaurantService restaurantService;
	private UserService userService;

	@Autowired
	public UserController(UserService service, RestaurantService restaurant_Service){
		this.userService = service;
		this.restaurantService = restaurant_Service;
	}

	// produces is good practice to include.
	@Secured(allowedRoles = {"Admin", "Manager"})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AppUser> getAllUsers(){
		return userService.getAllUser();
	}

	@GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUser getUserById(@PathVariable int id){
		return userService.getUserById(id);
	}

	@GetMapping(value = "/search")
	public AppUser getUserByUsername(@RequestParam String username){
		return userService.getUserByUsername(username);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public AppUser registerUser(@RequestBody Credentials creds){
		AppUser newUser = new AppUser(creds.getUsername(), creds.getPassword(), creds.getEmail());
		return userService.register(newUser);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/likes",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean addLikedRestaurant(@RequestBody UserVote vote){
		try{
			AppUser user = userService.getUserById(vote.getUser_id());
			Restaurant restaurant = restaurantService.getRestaurantByPlaceId(vote.getRestaurant_place_id());
			user.addLike(restaurant);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean ValidateLikedRestaurant(@RequestParam String user, @RequestParam String rest_vote){
		try{
			AppUser appUser = userService.getUserById(Integer.parseInt(user));
			Restaurant restaurant = restaurantService.getRestaurantByPlaceId(rest_vote);
			if(appUser.getLikes().contains(restaurant)) return true;
			else return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}







}
