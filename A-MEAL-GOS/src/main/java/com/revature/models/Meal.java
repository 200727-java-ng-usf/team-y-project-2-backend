package com.revature.models;

import javax.persistence.*;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "amg_meals", schema = "amealgos")
public class Meal {

	//region Fields
	@Id
	@Column(name="amg_meal_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "num_votes", columnDefinition = "integer default 3")
	private int numVotes;

	@Column(name = "meal_name", nullable = false)
	private String mealName;

	@Column(name = "final_restaurant_id", columnDefinition = "text")
	private String finalRestaurant;

	@OneToMany
	@JoinTable(
			name = "amg_meal_restaurants",
			schema = "amealgos",
			joinColumns = @JoinColumn(name = "amg_meal_id"),
			inverseJoinColumns = @JoinColumn(name = "amg_restaurant_id")
	)
	private Set<Restaurant> restaurants;

	@OneToMany
	@JoinTable(
			name = "amg_meal_users",
			schema = "amealgos",
			joinColumns = @JoinColumn(name = "amg_meal_id"),
			inverseJoinColumns = @JoinColumn(name = "amg_user_id")
	)
	private Set<AppUser> usersInMeal;

	@OneToMany
	@JoinTable(
			name = "amg_meal_users_voted",
			schema = "amealgos",
			joinColumns = @JoinColumn(name = "amg_meal_id"),
			inverseJoinColumns = @JoinColumn(name = "amg_user_id")
	)
	private Set<AppUser> usersFinishedVoting;
	//endregion

	public Meal() {
	}

	public Meal(int id, int numVotes, String mealName, String finalRestaurant, Set<Restaurant> restaurants, Set<AppUser> usersInMeal, Set<AppUser> usersFinishedVoting) {
		this.id = id;
		this.numVotes = numVotes;
		this.mealName = mealName;
		this.finalRestaurant = finalRestaurant;
		this.restaurants = restaurants;
		this.usersInMeal = usersInMeal;
		this.usersFinishedVoting = usersFinishedVoting;
	}

	public Meal(int numVotes, String mealName, String finalRestaurant, Set<Restaurant> restaurants, Set<AppUser> usersInMeal) {
		this.numVotes = numVotes;
		this.mealName = mealName;
		this.finalRestaurant = finalRestaurant;
		this.restaurants = restaurants;
		this.usersInMeal = usersInMeal;
	}

	public Meal(int id, int numVotes, String mealName, String finalRestaurant, Set<Restaurant> restaurants, Set<AppUser> usersInMeal) {
		this(numVotes, mealName, finalRestaurant, restaurants, usersInMeal);
		this.id = id;
	}

	public Meal( int numVotes, String mealName, Set<Restaurant> restaurants) {
		this.numVotes = numVotes;
		this.mealName = mealName;
		this.restaurants = restaurants;
	}

	public Meal(String mealName, Set<Restaurant> restaurants) {
		this.numVotes = 3;
		this.mealName = mealName;
		this.restaurants = restaurants;
	}

	public Meal(int numVotes, String mealName) {
		this.numVotes = numVotes;
		this.mealName = mealName;
	}

	public Meal(String mealName) {
		this.mealName = mealName;
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumVotes() {
		return numVotes;
	}

	public void setNumVotes(int numVotes) {
		this.numVotes = numVotes;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public String getFinalRestaurant() {
		return finalRestaurant;
	}

	public void setFinalRestaurant(String finalRestaurant) {
		this.finalRestaurant = finalRestaurant;
	}

	public Set<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Set<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public Set<AppUser> getUsersInMeal() {
		return usersInMeal;
	}

	public void setUsersInMeal(Set<AppUser> usersInMeal) {
		this.usersInMeal = usersInMeal;
	}

	public void addUsersInMeal(AppUser user) {
		this.usersInMeal.add(user);
	}

	public void addUsersFinishedVoting(AppUser user) {
		this.usersFinishedVoting.add(user);
	}

	public Set<AppUser> getUsersFinishedVoting() {
		return usersFinishedVoting;
	}

	public void setUsersFinishedVoting(Set<AppUser> usersFinishedVoting) {
		this.usersFinishedVoting = usersFinishedVoting;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Meal meal = (Meal) o;
		return id == meal.id &&
				numVotes == meal.numVotes &&
				Objects.equals(mealName, meal.mealName) &&
				Objects.equals(finalRestaurant, meal.finalRestaurant) &&
				Objects.equals(restaurants, meal.restaurants) &&
				Objects.equals(usersInMeal, meal.usersInMeal) &&
				Objects.equals(usersFinishedVoting, meal.usersFinishedVoting);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, numVotes, mealName, finalRestaurant, restaurants, usersInMeal, usersFinishedVoting);
	}

	@Override
	public String toString() {
		return "Meal{" +
				"id=" + id +
				", numVotes=" + numVotes +
				", mealName='" + mealName + '\'' +
				", finalRestaurant='" + finalRestaurant + '\'' +
				", restaurants=" + restaurants +
				", usersInMeal=" + usersInMeal +
				", usersFinishedVoting=" + usersFinishedVoting +
				'}';
	}
}
