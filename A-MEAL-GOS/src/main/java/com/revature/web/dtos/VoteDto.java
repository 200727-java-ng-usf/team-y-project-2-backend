package com.revature.web.dtos;

import com.revature.models.AppUser;
import com.revature.models.Meal;
import com.revature.models.Restaurant;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

public class VoteDto {
	private int restaurant;
	private int meal;
	private int user;
	private short vote;

	public VoteDto() {
	}

	public VoteDto(int restaurant, int meal, int user, short vote) {

		this.restaurant = restaurant;
		this.meal = meal;
		this.user = user;
		this.vote = vote;
	}



	public int getRestaurant() {

		return restaurant;
	}

	public void setRestaurant(int restaurant) {
		this.restaurant = restaurant;
	}

	public int getMeal() {
		return meal;
	}

	public void setMeal(int meal) {
		this.meal = meal;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public short getVote() {
		return vote;
	}

	public void setVote(short vote) {
		this.vote = vote;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VoteDto voteDto = (VoteDto) o;
		return restaurant == voteDto.restaurant &&
				meal == voteDto.meal &&
				user == voteDto.user &&
				vote == voteDto.vote;
	}

	@Override
	public int hashCode() {
		return Objects.hash(restaurant, meal, user, vote);
	}

	@Override
	public String toString() {
		return "VoteDto{" +
				"restaurant=" + restaurant +
				", meal=" + meal +
				", user=" + user +
				", vote=" + vote +
				'}';
	}
}
