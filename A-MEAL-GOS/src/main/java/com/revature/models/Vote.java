package com.revature.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "amg_votes", schema = "amealgos")
public class Vote {

	//region Fields
	@Id
	@Column(name="amg_vote_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@ManyToOne
	@JoinColumn(name = "vote_meal_id")
	private Meal meal;

	@ManyToOne
	@JoinColumn(name = "amg_user_id")
	private AppUser user;

	// 0 = absent vote, + = yes vote, - = no vote
	@Column(name = "amg_vote", columnDefinition = "smallint default 0")
	private short vote;

	//endregion

	public Vote() {
	}

	public Vote(Restaurant restaurant, Meal meal, AppUser user, short vote) {
		this.restaurant = restaurant;
		this.meal = meal;
		this.user = user;
		this.vote = vote;
	}

	public Vote(int id, Restaurant restaurant, Meal meal, AppUser user, short vote) {
		this(restaurant, meal, user, vote);
		this.id = id;
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
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
		Vote vote1 = (Vote) o;
		return id == vote1.id &&
				vote == vote1.vote &&
				Objects.equals(restaurant, vote1.restaurant) &&
				Objects.equals(meal, vote1.meal) &&
				Objects.equals(user, vote1.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, restaurant, meal, user, vote);
	}

	@Override
	public String toString() {
		return "Vote{" +
				"id=" + id +
				", restaurant=" + restaurant +
				", meal=" + meal +
				", user=" + user +
				", vote=" + vote +
				'}';
	}
}
