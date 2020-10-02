package com.revature.web.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.AppUser;

import java.util.Objects;

/**
 * The Data Transfer Object for Credentials, including
 * Username
 * Password
 * Email
 */
public class Credentials {

	private String username;
	private String password;
	private String email;

	public Credentials() {
	}

	public Credentials(AppUser user){
		this.username = user.getUsername();
		this.password = null;
		this.email = user.getEmail();
	}

	public Credentials(String email, String password){
		this.username = null;
		this.password = password;
		this.email = email;
	}

	public Credentials(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Credentials that = (Credentials) o;
		return Objects.equals(username, that.username) &&
				Objects.equals(password, that.password) &&
				Objects.equals(email, that.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password, email);
	}

	@Override
	public String toString() {
		return "Credentials{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
