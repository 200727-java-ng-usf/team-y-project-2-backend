package com.revature.services;

import com.revature.daos.MealDao;
import com.revature.exceptions.AmealgoException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Models all services and operations that might apply to <code>{@link Meal}</code>s.
 */
@Service
public class MealService {
	/**
	 * An <code>{@link Meal}</code> Data Access Object Instance.
	 */
	private MealDao mealDao;

	@Autowired
	public MealService(MealDao mealDao){
		this.mealDao = mealDao;
	}


	//region Methods

	/**
	 * Returns all restaurants registered within the database.
	 * @return a Set of <code>{@link Meal}</code>s that have been registered and saved to the database
	 */
	@Transactional(readOnly = true)
	public List<Meal> getAllMeals() throws ResourceNotFoundException {
		List<Meal> users = new ArrayList<>();
		try{
			users = mealDao.findAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(users.isEmpty()){
			throw new ResourceNotFoundException();
		}
		return users;
	}

	/**
	 * Returns the first <code>{@link Meal}</code> found with the given id.
	 * @param id the int id to search by
	 * @return the first <code>{@link Meal}</code> found with the given id.
	 */
	@Transactional(readOnly = true)
	public Meal getMealById(int id) throws ResourceNotFoundException {
		if(id <= 0){
			throw new InvalidRequestException("The provided id cannot be less than or equal to zero.");
		}

		try{
			return mealDao.findById(id).orElseThrow(ResourceNotFoundException::new);
		} catch(Exception e) {
			throw new AmealgoException(e);
		}
	}

	/**
	 * Returns the first <code>{@link Meal}</code> found with the given username.
	 * @param name the String name to search by.
	 * @return the first <code>{@link Meal}</code> found with the given name.
	 */
	@Transactional(readOnly = true)
	public Meal getMealByName(String name){
		if(name == null || name.equals("")){
			throw new InvalidRequestException("Username cannot be null or empty.");
		}
		try{
			return mealDao.findMealByName(name)
					.orElseThrow(ResourceNotFoundException::new);
		} catch(Exception e) {
			throw new AmealgoException(e);
		}
	}


	/**
	 * This method updates the records of a <code>{@link Meal}</code>
	 * that exists on the database with the local record.
	 * @param restaurant the <code>{@link Meal}</code> to update.
	 * @return returns true if the update was successful, false otherwise.
	 */
	@Transactional(readOnly = false)
	public boolean updateMeal(Meal restaurant){
		return mealDao.update(restaurant);
	}

	/**
	 * This method deletes a <code>{@link Meal}</code> with the given id from
	 * the the database, along with any records only pertinent to them.
	 * @param id the id of the <code>{@link Meal}</code>.
	 * @return returns true if the deletion was successful, false if otherwise.
	 * 		If there was no such user, returns true.
	 */
	@Transactional(readOnly = false)
	public boolean deleteMealById(int id){
		return mealDao.deleteById(id);
	}

	//endregion
}
