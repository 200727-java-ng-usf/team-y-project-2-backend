package com.revature.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "amg_likes", schema = "amealgos")
public class Like {

	//region Fields
	@Id
	@Column(name="amg_like_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "restaurant_place_id", nullable = false, columnDefinition = "text")
	private String restaurant;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	//endregion

	//region Constructors
	public Like() {
	}

	public Like(int id, String restaurant, AppUser user) {

		this.id = id;
		this.restaurant = restaurant;
		this.user = user;
	}
	//endregion

	//region Getters and Setters

	public int getId() {

		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}
	//endregion

	//region Overridden Methods
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Like like = (Like) o;
		return id == like.id &&
				Objects.equals(restaurant, like.restaurant) &&
				Objects.equals(user, like.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, restaurant, user);
	}

	@Override
	public String toString() {
		return "Like{" +
				"id=" + id +
				", restaurant='" + restaurant + '\'' +
				", user=" + user +
				'}';
	}
	//endregion
}
