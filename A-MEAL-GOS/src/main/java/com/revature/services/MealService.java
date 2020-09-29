package com.revature.services;

<<<<<<< HEAD
public class MealService {
=======
import com.revature.daos.MealDao;
import com.revature.exceptions.*;
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
	public MealService(MealDao MealDao){
		this.mealDao = MealDao;
	}

	//region Methods

	/**
	 * Returns all Meals registered with the bank database.
	 * @return a Set of <code>{@link Meal}</code>s that have been registered and saved to the bank database
	 */
	@Transactional(readOnly = true)
	public List<Meal> getAllMeals() throws ResourceNotFoundException {
		List<Meal> Meals = new ArrayList<>();
		try{
			Meals = mealDao.findAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(Meals.isEmpty()){
			throw new ResourceNotFoundException();
		}
		return Meals;
	}

	/**
	 * Returns the first <code>{@link Meal}</code> found with the given id.
	 * @param id the int id to search by
	 * @return the first <code>{@link Meal}</code> found with the given id.
	 */
	@Transactional(readOnly = true)
	public Meal getMealById(int id) throws ResourceNotFoundException {
		if(id <= 0){
			throw new InvalidRequestException("The provided code cannot be less than or equal to zero.");
		}

		try{
			return mealDao.findById(id)
					.orElseThrow(ResourceNotFoundException::new);
		} catch(Exception e) {
			throw new AmealgoException(e);
		}
	}

	/**
	 * This method registers a new <code>{@link Meal}</code> into the database.
	 * @param newMeal the <code>{@link Meal}</code> to store/register in the database.
	 */
	@Transactional
	public Meal create(Meal newMeal){

		if(!isMealValid(newMeal)){
			throw new InvalidRequestException("Invalid Meal field values provided during registration!");
		}

		try{
			mealDao.save(newMeal);
		} catch(Exception e) {
			throw new ResourcePersistenceException("Could not persist new Meal!");
		}

		return newMeal;
	}

	/**
	 * This method updates the records of a <code>{@link Meal}</code>
	 * that exists on the database with the local record.
	 * @param Meal the <code>{@link Meal}</code> to update.
	 * @return returns true if the update was successful, false otherwise.
	 */
	@Transactional(readOnly = false)
	public boolean updateMeal(Meal Meal){
		return mealDao.update(Meal);
	}

	/**
	 * This method deletes a <code>{@link Meal}</code> with the given id from
	 * the the database, along with any records only pertinent to them.
	 * @param id the id of the <code>{@link Meal}</code>.
	 * @return returns true if the deletion was successful, false if otherwise.
	 * 		If there was no such Meal, returns true.
	 */
	//may not need....
	@Transactional(readOnly = false)
	public boolean deleteMealById(int id){
		return mealDao.deleteById(id);
	}

	/**
	 * This method deletes a <code>{@link Meal}</code> with the given id from
	 * the the database, along with any records only pertinent to them.
	 * @param Mealname the id of the <code>{@link Meal}</code>.
	 * @return returns true if the deletion was successful, false if otherwise.
	 * 		If there was no such Meal, returns true.
	 */
	@Transactional(readOnly = false)
	public boolean deleteMealByMealname(String Mealname){
		return mealDao.deleteByMealname(Mealname);
	}


	/**
	 * Validates that the given <code>{@link Meal}</code> and its fields are
	 * valid (not null or empty strings). Does not perform validation on id or Role fields.
	 *
	 * @param Meal the <code>{@link Meal}</code> to validate.
	 * @return true if the <code>{@link Meal}</code> is valid.
	 */
	public boolean isMealValid(Meal Meal){
		if(Meal == null) return false;
		if(Meal.getMealName() == null || Meal.getMealName().trim().equals("")) return false;
		return true;
	}
	//endregion
>>>>>>> 0800ca30217832ca6068d399905ec4bdacf32b94
}
