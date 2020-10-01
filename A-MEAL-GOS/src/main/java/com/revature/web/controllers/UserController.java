package com.revature.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.AppUser;
import com.revature.models.Restaurant;
import com.revature.services.UserService;
import com.revature.web.dtos.Credentials;
import com.revature.web.dtos.Principal;
import com.revature.web.security.Secured;
import jdk.packager.internal.ModuleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

// @RestController
// 		implies @Controller at the class level
// 		@Responsebody on the return type of each mapping method.
@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService service){
		this.userService = service;
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

	@GetMapping(value = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<Restaurant> getUserLikesWithPrincipal(HttpServletRequest req){
		try {
			Object o = req.getSession().getAttribute("principal");
			ObjectMapper mapper = new ObjectMapper();
			Principal principal = mapper.readValue((String) o,Principal.class);
			return userService.getUserById(principal.getId()).getLikes();

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	@GetMapping(value = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<Restaurant> getUserLikesWithUserId(int id) {

			return userService.getUserById(id).getLikes();



	}



}
