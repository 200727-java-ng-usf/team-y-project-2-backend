package com.revature.web.dtos;

import java.util.Objects;

public class MealDto {
	private int numVotes;
	private String mealName;
	private String finalRestaurant;

	public MealDto() {
	}

	public MealDto(int numVotes, String mealName, String finalRestaurant) {

		this.numVotes = numVotes;
		this.mealName = mealName;
		this.finalRestaurant = finalRestaurant;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MealDto mealDto = (MealDto) o;
		return numVotes == mealDto.numVotes &&
				Objects.equals(mealName, mealDto.mealName) &&
				Objects.equals(finalRestaurant, mealDto.finalRestaurant);
	}

	@Override
	public int hashCode() {
		return Objects.hash(numVotes, mealName, finalRestaurant);
	}

	@Override
	public String toString() {
		return "MealDto{" +
				"numVotes=" + numVotes +
				", mealName='" + mealName + '\'' +
				", finalRestaurant='" + finalRestaurant + '\'' +
				'}';
	}
}
