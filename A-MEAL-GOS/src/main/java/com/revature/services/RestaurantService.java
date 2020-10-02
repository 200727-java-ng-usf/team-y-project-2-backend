package com.revature.services;

import com.revature.daos.RestaurantDao;
import com.revature.exceptions.*;
import com.revature.models.Restaurant;
import com.revature.models.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Models all services and operations that might apply to <code>{@link Meal}</code>s.
 */
@Service
public class RestaurantService {
	/**
	 * An <code>{@link Restaurant}</code> Data Access Object Instance.
	 */
	private RestaurantDao restaurantDao;

	@Autowired
	public RestaurantService(RestaurantDao restaurantDao){
		this.restaurantDao = restaurantDao;
	}


	//region Methods

	/**
	 * Returns all restaurants registered within the database.
	 * @return a List of <code>{@link Restaurant}</code>s that have been registered and saved to the database
	 * @throws ResourceNotFoundException
	 */
	@Transactional(readOnly = true)
	public List<Restaurant> getAllRestaurant() throws ResourceNotFoundException {
		List<Restaurant> users = new ArrayList<>();
		try{
			users = restaurantDao.findAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(users.isEmpty()){
			throw new ResourceNotFoundException();
		}
		return users;
	}

	/**
	 * Returns all restaurants registered within the database in the meal with the given meal id.
	 * @param mealId the int id of the meal to search for
	 * @return a List of <code>{@link Restaurant}</code>s that are associated with the meal with the given meal id
	 * @throws ResourceNotFoundException
	 */
	@Transactional(readOnly = true)
	public List<Restaurant> getMealRestaurants(int mealId) throws ResourceNotFoundException {
		List<Restaurant> restaurants = new ArrayList<>();
		try{
			restaurants = restaurantDao.findMealRestaurants(mealId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(restaurants.isEmpty()){
			throw new ResourceNotFoundException();
		}
		return restaurants;
	}


	/**
	 * Returns the first <code>{@link Restaurant}</code> found with the given id.
	 * @param id the int id to search by
	 * @return the first <code>{@link Restaurant}</code> found with the given id.
	 */
	@Transactional(readOnly = true)
	public Restaurant getRestaurantById(int id) throws ResourceNotFoundException {
		if(id <= 0){
			throw new InvalidRequestException("The provided id cannot be less than or equal to zero.");
		}

		try{
			return restaurantDao.findById(id).orElseThrow(ResourceNotFoundException::new);
		} catch(Exception e) {
			throw new AmealgoException(e);
		}
	}

	/**
	 * Returns the first <code>{@link Restaurant}</code> found with the given place id.
	 * @param placeId id the int place id to search by.
	 * @return the first <code>{@link Restaurant}</code> found with the given place id.
	 */
	@Transactional(readOnly = true)
	public Restaurant getRestaurantByPlaceId(String placeId){
		if(placeId == null || placeId.equals("")){
			throw new InvalidRequestException("Place id cannot be null or empty.");
		}
		try{
			return restaurantDao.findRestaurantByPlace(placeId)
					.orElseThrow(ResourceNotFoundException::new);
		} catch(Exception e) {
			throw new AmealgoException(e);
		}
	}


	/**
	 * This method updates the records of a <code>{@link Restaurant}</code>
	 * that exists on the database with the local record.
	 * @param restaurant the <code>{@link Restaurant}</code> to update.
	 * @return returns true if the update was successful, false otherwise.
	 */
	@Transactional(readOnly = false)
	public boolean updateRestaurant(Restaurant restaurant){
		return restaurantDao.update(restaurant);
	}

	/**
	 * This method deletes a <code>{@link Restaurant}</code> with the given id from
	 * the the database, along with any records only pertinent to them.
	 * @param id the id of the <code>{@link Restaurant}</code>.
	 * @return returns true if the deletion was successful, false if otherwise.
	 * 		If there was no such user, returns true.
	 */
	@Transactional(readOnly = false)
	public boolean deleteRestaurantById(int id){
		return restaurantDao.deleteById(id);
	}

	@Transactional
	public Restaurant createRestaurant(Restaurant restaurant){

		restaurantDao.saveRestaurant(restaurant);

		return restaurant;
	}

	@Transactional
	public Set<Restaurant> createRestaurants (Set<Restaurant> restaurants){

		restaurants.stream().forEach(restaurant -> createRestaurant(restaurant));

		return restaurants;
	}
	//endregion
}
