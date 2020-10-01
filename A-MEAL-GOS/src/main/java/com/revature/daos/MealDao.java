package com.revature.daos;

import com.revature.models.Meal;
import com.revature.models.Vote;
import com.revature.web.dtos.ResultDto;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The <code>{@link Meal}</code> Data Access Object
 * Implements <code>{@link CrudDao}</code><<code>{@link Meal}</code>>
 */
@Repository
public class MealDao implements CrudDao<Meal> {

	/**
	 * The <code>{@link SessionFactory}</code> sessionFactory instance.
	 */
	private SessionFactory sessionFactory;

	@Autowired
	public MealDao(SessionFactory factory){
		sessionFactory = factory;
	}
	/**
	 * Saves the given <code>{@link Meal}</code> to the repository.
	 * @param meal the <code>{@link Meal}</code> to save to the repository
	 */
	@Override
	public Optional<Meal> save(Meal meal) {
		Session session = sessionFactory.getCurrentSession();
		session.save(meal);
		return Optional.of(meal);
	}

	/**
	 * Returns all <code>{@link Meal}</code>s in the repository in an <code>{@link ArrayList}</code><<code>{@link Meal}</code>>
	 * @return an <code>{@link ArrayList}</code><<code>{@link Meal}</code>> of all <code>{@link Meal}</code>s in the repository.
	 */
	@Override
	public List<Meal> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Meal", Meal.class).getResultList();
	}

	/**
	 * Returns an <code>{@link Meal}</code>s with the given id.
	 * @param id the id associated with the <code>{@link Meal}</code>.
	 * @return an <code>{@link Meal}</code>s with the given id.
	 * 			If no <code>{@link Meal}</code> is found, returns null.
	 */
	@Override
	public Optional<Meal> findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return Optional.of(session.get(Meal.class, id));

	}

	/**
	 * Returns true if a successful update occurs.
	 * @param meal the <code>{@link Meal}</code> to update.
	 * @return true if update was successful.
	 */
	@Override
	public boolean update(Meal meal) {
		Session session = sessionFactory.getCurrentSession();
		try{
			session.update(meal);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes an <code>{@link Meal}</code> by the <code>{@link Meal}</code>s id.
	 * @param id the id of the <code>{@link Meal}</code> to delete.
	 * @return true if the deletion was successful.
	 */
	@Override
	public boolean deleteById(int id) {
		Session session = sessionFactory.getCurrentSession();
		try{
			Meal meal = session.load(Meal.class, id);
			session.delete(meal);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns an <code>{@link Optional}</code><<code>{@link Meal}</code>> with the given name.
	 * @param mealName the name associated with the desired <code>{@link Meal}</code>
	 * @return an <code>{@link Optional}</code><<code>{@link Meal}</code>> with the given name.
	 * 			If none match the name, an <code>{@link Optional}</code>.empty() is returned.
	 */
	public Optional<Meal> findMealByName(String mealName) {
		Session session = sessionFactory.getCurrentSession();
		return Optional.ofNullable(session.createQuery("from Meal ml where ml.mealName = :ml", Meal.class)
				.setParameter("ml", mealName)
				.getSingleResult());
	}


	/**
	 * Deletes an <code>{@link Meal}</code> by the <code>{@link Meal}</code>s id.
	 * @param mealname the id of the <code>{@link Meal}</code> to delete.
	 * @return true if the deletion was successful.
	 */
	public boolean deleteByMealname(String mealname) {
		Session session = sessionFactory.getCurrentSession();
		try{
			Meal meal = session.load(Meal.class, mealname);
			session.delete(meal);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}

	public List<Vote> findWinningRestaurant(int mealId) {

		Session session = sessionFactory.getCurrentSession();
		session.createQuery("from Vote where vote_meal_id = :id")
				.setParameter("id", mealId)
				.getResultList();




//		Query query = session.createQuery(
//				"SELECT new com.revature.web.dtos.ResultDto(" +
//				"SUM (av.amg_vote) AS total, " +
//				"ar.restaurant_name, " +
//				"ar.address) " +
//				"FROM " +
//				"com.revature.models.Vote av " +
//				"JOIN  " +
//				"com.revature.models.Restaurant ar " +
//				"ON  " +
//				"av.restaurant = ar.id  " +
//				"WHERE  " +
//				"av.meal = :id " +
//				"GROUP BY " +
//				"av.restaurant, " +
//				"ar.name, " +
//				"ar.address " +
//				"ORDER BY total DESC")
//				.setParameter("id", winner);
//		List<ResultDto> resultList = query.list();
//		ResultDto result = resultList.get(0);
//
//		System.out.println(result);
//		return result;

//			Query query = session.createSQLQuery(
//					"SELECT " +
//							"SUM (av.vote) AS total, " +
//							"ar.name, " +
//							"ar.address " +
//							"FROM " +
//							"com.revature.models.Vote av " +
//							"JOIN  " +
//							"com.revature.models.Restaurant ar " +
//							"ON  " +
//							"av.restaurant = ar.id  " +
//							"WHERE  " +
//							"av.meal = :id " +
//							"GROUP BY " +
//							"av.restaurant, " +
//							"ar.name, " +
//							"ar.address " +
//							"ORDER BY total DESC")
//					.setParameter("id", winner);
//					//.setResultTransformer(Transformers.aliasToBean(ResultDto.class));
//
//			ResultDto result = (ResultDto) query.getSingleResult();

//			Query<ResultDto> query = session.createQuery(
//					"SELECT new com.revature.web.dtos.ResultDto(" +
//							"SUM (av.vote) AS total, " +
//							"ar.name, " +
//							"ar.address) " +
//							"FROM " +
//							"com.revature.models.Vote av " +
//							"JOIN  " +
//							"com.revature.models.Restaurant ar " +
//							"ON  " +
//							"av.restaurant = ar.id  " +
//							"WHERE  " +
//							"av.meal = :id " +
//							"GROUP BY " +
//							"av.restaurant, " +
//							"ar.name, " +
//							"ar.address " +
//							"ORDER BY total DESC", ResultDto.class)
//							.setParameter("id", winner);
//			resultDto = query.getSingleResult();

		return null;
	}
}
