package com.revature.web.dtos;

import java.util.Objects;

public class UserVote {
	private int user_id;
	private String restaurant_place_id;

	public UserVote() {
	}

	public UserVote(int user_id, String restaurant_place_id) {

		this.user_id = user_id;
		this.restaurant_place_id = restaurant_place_id;
	}

	public int getUser_id() {

		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getRestaurant_place_id() {
		return restaurant_place_id;
	}

	public void setRestaurant_place_id(String restaurant_place_id) {
		this.restaurant_place_id = restaurant_place_id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserVote userVote = (UserVote) o;
		return user_id == userVote.user_id &&
				Objects.equals(restaurant_place_id, userVote.restaurant_place_id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user_id, restaurant_place_id);
	}

	@Override
	public String toString() {
		return "UserVote{" +
				"user_id=" + user_id +
				", restaurant_place_id='" + restaurant_place_id + '\'' +
				'}';
	}
}
