package com.revature.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.services.UserService;
import com.revature.web.dtos.Credentials;
import com.revature.web.dtos.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private UserService userService;

	@Autowired
	public AuthController(UserService service){
		this.userService = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void invalidateSession(HttpServletRequest req){
		req.getSession().invalidate();
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Principal authenticate(@RequestBody Credentials creds, HttpServletRequest req) throws JsonProcessingException {
		Principal payload = userService.authenticate(creds);
		HttpSession userSession = req.getSession();
		userSession.setAttribute("principal", payload.stringify());
		return payload;
	}
}
