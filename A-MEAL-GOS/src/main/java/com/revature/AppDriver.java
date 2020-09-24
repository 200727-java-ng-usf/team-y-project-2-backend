package com.revature;

import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.AppUser;
import com.revature.services.UserService;
import com.revature.util.ApplicationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class AppDriver {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext container = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		container.registerShutdownHook();

		UserService userService = container.getBean("userService", UserService.class);
		List<AppUser> users = null;
		try {
			users = userService.getAllUser();
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		users.forEach(System.out::println);
	}
}
