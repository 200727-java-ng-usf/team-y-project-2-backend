package com.revature.daos;

import com.revature.models.AppUser;
import com.revature.models.Meal;
import com.revature.models.Restaurant;
import com.revature.models.Vote;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The <code>{@link Vote}</code> Data Access Object
 * Implements <code>{@link CrudDao}</code><<code>{@link Vote}</code>>
 */
@Repository
public class VoteDao implements CrudDao<Vote> {

	/**
	 * The <code>{@link SessionFactory}</code> sessionFactory instance.
	 */
	private SessionFactory sessionFactory;

	@Autowired
	public VoteDao(SessionFactory factory){
		sessionFactory = factory;
	}
	/**
	 * Saves the given <code>{@link Vote}</code> to the repository.
	 * @param vote the <code>{@link Vote}</code> to save to the repository
	 */
	@Override
	public Optional<Vote> save(Vote vote) {
		Session session = sessionFactory.getCurrentSession();
		session.save(vote);
		return Optional.of(vote);
	}

	/**
	 * Returns all <code>{@link Vote}</code>s in the repository in an <code>{@link ArrayList}</code><<code>{@link Vote}</code>>
	 * @return an <code>{@link ArrayList}</code><<code>{@link Vote}</code>> of all <code>{@link Vote}</code>s in the repository.
	 */
	@Override
	public List<Vote> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Vote", Vote.class).getResultList();
	}

	/**
	 * Returns an <code>{@link Vote}</code>s with the given id.
	 * @param id the id associated with the <code>{@link Vote}</code>.
	 * @return an <code>{@link Vote}</code>s with the given id.
	 * 			If no <code>{@link Vote}</code> is found, returns null.
	 */
	@Override
	public Optional<Vote> findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return Optional.of(session.get(Vote.class, id));
	}

	/**
	 * Returns true if a successful update occurs.
	 * @param vote the <code>{@link Vote}</code> to update.
	 * @return true if update was successful.
	 */
	@Override
	public boolean update(Vote vote) {
		Session session = sessionFactory.getCurrentSession();
		try{
			session.update(vote);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes an <code>{@link Vote}</code> by the <code>{@link Vote}</code>s id.
	 * @param id the id of the <code>{@link Vote}</code> to delete.
	 * @return true if the deletion was successful.
	 */
	@Override
	public boolean deleteById(int id) {
		Session session = sessionFactory.getCurrentSession();
		try{
			Vote vote = session.load(Vote.class, id);
			session.delete(vote);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns an <code>{@link Optional}</code><<code>{@link Vote}</code>> with the given name.
	 * @param restaurant the name associated with the desired <code>{@link Vote}</code>
	 * @return an <code>{@link Optional}</code><<code>{@link Vote}</code>> with the given name.
	 * 			If none match the name, an <code>{@link Optional}</code>.empty() is returned.
	 */
	public List<Vote> findVotesByRestaurant(Restaurant restaurant) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Vote  vt where  vt.restaurant = :restaurant", Vote.class)
				.setParameter("restaurant", restaurant)
				.getResultList();
	}

	/**
	 * Returns an <code>{@link Optional}</code><<code>{@link Vote}</code>> with the given name.
	 * @param meal the name associated with the desired <code>{@link Vote}</code>
	 * @return an <code>{@link Optional}</code><<code>{@link Vote}</code>> with the given name.
	 * 			If none match the name, an <code>{@link Optional}</code>.empty() is returned.
	 */
	public List<Vote> findVotesByMeal(Meal meal) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Vote vt where  vt.meal = :meal", Vote.class)
				.setParameter("meal", meal)
				.getResultList();
	}

	/**
	 * Returns an <code>{@link Optional}</code><<code>{@link Vote}</code>> with the given name.
	 * @param user the name associated with the desired <code>{@link Vote}</code>
	 * @return an <code>{@link Optional}</code><<code>{@link Vote}</code>> with the given name.
	 * 			If none match the name, an <code>{@link Optional}</code>.empty() is returned.
	 */
	public List<Vote> findVotesByUser(AppUser user) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Vote vt where vt.user = :user", Vote.class)
				.setParameter("user", user)
				.getResultList();
	}

	@Transactional(readOnly = false)
	public List<Vote> findCastVotesByUser(AppUser user) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Vote vt where vt.user = :user and vt.vote = 1", Vote.class)
				.setParameter("user", user)
				.getResultList();
	}
}
