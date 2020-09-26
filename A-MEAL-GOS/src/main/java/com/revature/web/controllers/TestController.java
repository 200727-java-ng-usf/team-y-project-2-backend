package com.revature.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class TestController {

	@GetMapping(value="/test1")
	public @ResponseBody String test(){
		return "/test works!";
	}

	@GetMapping(value="/test2")
	public @ResponseBody String test2(){
		return "/test/test2 works!";
	}

	@GetMapping(value = "/test3")
	public @ResponseBody String test3(@RequestParam("someValue") String someValue){
		return "/test/test3 works! Provided request param: " + someValue;
	}

	@GetMapping(value = "/test4/{anotherValue}")
	public @ResponseBody String test4(@PathVariable("anotherValue") String anotherValue){
		return "/test/test4 works! Provided path variable: " + anotherValue;
	}

	@GetMapping(value = "/home")
	public String home(){
		return "home";
	}

	@GetMapping(value = "/test5")
	public @ResponseBody String test5(@RequestHeader("someHeader") String value){
		return value;
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@GetMapping(value = "/test6")
	public void test6(){
		System.out.println("");
	}
}
