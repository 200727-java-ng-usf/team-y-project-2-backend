package com.revature.web.dtos;

import javax.persistence.Column;
import java.util.Objects;

public class RestaurantDto {

	private String place;
	private String name;
	private String address;

	public RestaurantDto() {
	}

	public RestaurantDto(String place, String name, String address) {

		this.place = place;
		this.name = name;
		this.address = address;
	}

	public String getPlace() {

		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RestaurantDto that = (RestaurantDto) o;
		return Objects.equals(place, that.place) &&
				Objects.equals(name, that.name) &&
				Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(place, name, address);
	}

	@Override
	public String toString() {
		return "RestaurantDto{" +
				"place='" + place + '\'' +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
