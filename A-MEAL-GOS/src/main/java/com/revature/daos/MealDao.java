package com.revature.daos;

import com.revature.models.AppUser;
import com.revature.models.Meal;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	 * @param Meal the <code>{@link Meal}</code> to save to the repository
	 */
	@Override
	public Optional<Meal> save(Meal Meal) {
		Session session = sessionFactory.getCurrentSession();
		session.save(Meal);
		return Optional.of(Meal);
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
		return findMealById(id);
	}

	/**
	 * Returns true if a successful update occurs.
	 * @param Meal the <code>{@link Meal}</code> to update.
	 * @return true if update was successful.
	 */
	@Override
	public boolean update(Meal Meal) {
		Session session = sessionFactory.getCurrentSession();
		try{
			session.update(Meal);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}

		//TODO move to mealService
//		try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
//			tx = session.beginTransaction();
////			session.update(Meal);
//			String hql = "update Meal au ";
//			hql += "set au.active = :active, ";
//
//			// id
//			// active
//			// firstName
//			// lastName
//			// username
//			// passwordHash
//			// passwordSalt
//			// email
//			// role
//
//			if(Meal.getUsername() != null && !Meal.getUsername().trim().equals("")){
//				System.out.println("username present.");
//				hql += "au.username = :username, ";
//			}
//			if(Meal.getPasswordHash() != null && Meal.getPasswordHash().length > 0){
//				System.out.println("passwordHash present.");
//				hql += "au.passwordHash = :passwordHash, ";
//			}
//			if(Meal.getPasswordSalt() != null && Meal.getPasswordSalt().length > 0){
//				System.out.println("passwordSalt present.");
//				hql += "au.passwordSalt = :passwordSalt, ";
//			}
//			if(Meal.getEmail() != null && !Meal.getEmail().trim().equals("")){
//				System.out.println("email present.");
//				hql += "au.email = :email, ";
//			}
//			hql += "where au.id = :id ";
//			Query query = session.createQuery(hql);
//
//			if(Meal.getUsername() != null && !Meal.getUsername().trim().equals("")) {
//				query.setParameter("username", Meal.getUsername());
//			}
//			if(Meal.getPasswordHash() != null && Meal.getPasswordHash().length > 0) {
//				query.setParameter("passwordHash", Meal.getPasswordHash());
//			}
//			if(Meal.getPasswordSalt() != null && Meal.getPasswordSalt().length > 0) {
//				query.setParameter("passwordSalt", Meal.getPasswordSalt());
//			}
//			if(Meal.getEmail() != null && !Meal.getEmail().trim().equals("")) {
//				query.setParameter("email", Meal.getEmail());
//			}
//
//			query.setParameter("id", Meal.getId());
//			int result = query.executeUpdate();
//			if(result <= 0) return false;
//			tx.commit();
//		} catch(Exception e) {
//			e.printStackTrace();
//			if(tx != null) tx.rollback();
//			return false;
//		}
//		return true;
	}

	/**
	 * Deletes an <code>{@link Meal}</code> by the <code>{@link Meal}</code>s id.
	 * @param id the id of the <code>{@link Meal}</code> to delete.
	 * @return true if the deletion was successful.
	 */
	//may not use...
	@Override
	public boolean deleteById(int id) {
		Session session = sessionFactory.getCurrentSession();
		try{
			Meal user = session.load(Meal.class, id);
			session.delete(user);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
		//move to mealservice
//		try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
//			tx = session.beginTransaction();
//			Query query = session.createQuery("delete Meal au where au.id = :id ")
//					.setParameter("id", id);
//			int result = query.executeUpdate();
//			if(result <= 0) return false;
//			tx.commit();
//		} catch(Exception e) {
//			e.printStackTrace();
//			if(tx != null) tx.rollback();
//			return false;
//		}
//		return true;
	}

	/**
	 * Deletes an <code>{@link Meal}</code> by the <code>{@link Meal}</code>s name.
	 * @param mealName the mealName of the <code>{@link Meal}</code> to delete.
	 * @return true if the deletion was successful.
	 */
	public boolean deleteByMealname(String mealName) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Meal meal = session.load(Meal.class, mealName);
			session.delete(mealName);
			return true;
		} catch (HibernateException he) {
			he.printStackTrace();
			return false;
		}


		/**
		 * Returns an <code>{@link Optional}</code><<code>{@link Meal}</code>> with the given id.
		 * @param id the id associated with the desired <code>{@link Meal}</code>
		 * @return an <code>{@link Optional}</code><<code>{@link Meal}</code>> with the given id.
		 * 			If none match the id, an <code>{@link Optional}</code>.empty() is returned.
		 */

	}

	public Optional<Meal> findMealById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return Optional.of(session.get(Meal.class, id)); // get returns null, load throws an error.
	}

	public Meal findWinningVote() {
		Session session = sessionFactory.getCurrentSession();
		return null;
	}
}
