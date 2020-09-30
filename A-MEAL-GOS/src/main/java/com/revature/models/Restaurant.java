package com.revature.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "amg_restaurants", schema = "amealgos")
public class Restaurant {
	//region Fields

	@Id
	@Column(name="amg_restaurant_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "place_id", columnDefinition = "text")
	private String place;

	@Column(name = "restaurant_name", columnDefinition = "text")
	private String name;

	@Column(name = "address", columnDefinition = "text")
	private String address;





	//endregion

	//region Constructors

	public Restaurant() {
	}

	public Restaurant(String place, String name, String address) {

		this.place = place;
		this.name = name;
		this.address = address;
	}

	public Restaurant(int id, String place, String name, String address) {
		this(place, name, address);
		this.id = id;
	}
	//endregion

	//region Getters and Setters

	public int getId() {

		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	//endregion

	//region Overridden Methods

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Restaurant that = (Restaurant) o;
		return id == that.id &&
				Objects.equals(place, that.place) &&
				Objects.equals(name, that.name) &&
				Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, place, name, address);
	}

	@Override
	public String toString() {
		return "Restaurant{" +
				"id=" + id +
				", place='" + place + '\'' +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				'}';
	}
	//endregion
}
