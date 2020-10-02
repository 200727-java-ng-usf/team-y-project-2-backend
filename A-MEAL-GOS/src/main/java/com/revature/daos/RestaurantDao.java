package com.revature.daos;

import com.revature.models.Meal;
import com.revature.models.Restaurant;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The <code>{@link Restaurant}</code> Data Access Object
 * Implements <code>{@link CrudDao}</code><<code>{@link Restaurant}</code>>
 */
@Repository
public class RestaurantDao implements CrudDao<Restaurant> {

    /**
     * The <code>{@link SessionFactory}</code> sessionFactory instance.
     */
    private SessionFactory sessionFactory;

    @Autowired
    public RestaurantDao(SessionFactory factory) {
        sessionFactory = factory;
    }

    /**
     * Saves the given <code>{@link Restaurant}</code> to the repository.
     *
     * @param restaurant the <code>{@link Restaurant}</code> to save to the repository
     */
    @Override
    public Optional<Restaurant> save(Restaurant restaurant) {
        Session session = sessionFactory.getCurrentSession();
        session.save(restaurant);
        return Optional.of(restaurant);
    }

    /**
     * Returns all <code>{@link Restaurant}</code>s in the repository in an <code>{@link ArrayList}</code><<code>{@link Restaurant}</code>>
     *
     * @return an <code>{@link ArrayList}</code><<code>{@link Restaurant}</code>> of all <code>{@link Restaurant}</code>s in the repository.
     */
    @Override
    public List<Restaurant> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Restaurant", Restaurant.class).getResultList();
    }

    /**
     * Returns an <code>{@link Restaurant}</code>s with the given id.
     *
     * @param id the id associated with the <code>{@link Restaurant}</code>.
     * @return an <code>{@link Restaurant}</code>s with the given id.
     * If no <code>{@link Restaurant}</code> is found, returns null.
     */
    @Override
    public Optional<Restaurant> findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.of(session.get(Restaurant.class, id));
    }

    /**
     * Returns true if a successful update occurs.
     *
     * @param restaurant the <code>{@link Restaurant}</code> to update.
     * @return true if update was successful.
     */
    @Override
    public boolean update(Restaurant restaurant) {
        Session session = sessionFactory.getCurrentSession();
        try {
//			Restaurant user = session.load(Restaurant.class, username);
            session.update(restaurant);
            return true;
        } catch (HibernateException he) {
            he.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an <code>{@link Restaurant}</code> by the <code>{@link Restaurant}</code>s id.
     *
     * @param id the id of the <code>{@link Restaurant}</code> to delete.
     * @return true if the deletion was successful.
     */
    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Restaurant user = session.load(Restaurant.class, id);
            session.delete(user);
            return true;
        } catch (HibernateException he) {
            he.printStackTrace();
            return false;
        }
    }

    /**
     * Returns an <code>{@link Optional}</code><<code>{@link Restaurant}</code>> with the given name.
     *
     * @param name the name associated with the desired <code>{@link Restaurant}</code>
     * @return an <code>{@link Optional}</code><<code>{@link Restaurant}</code>> with the given name.
     * If none match the name, an <code>{@link Optional}</code>.empty() is returned.
     */
    public Optional<Restaurant> findRestaurantByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.createQuery("from Restaurant rs where rs.username = :un", Restaurant.class)
                .setParameter("un", name)
                .getSingleResult());
    }

    /**
     * Returns an <code>{@link Optional}</code><<code>{@link Restaurant}</code>> with the given place.
     *
     * @param place the place associated with the desired <code>{@link Restaurant}</code>
     * @return an <code>{@link Optional}</code><<code>{@link Restaurant}</code>> with the given place.
     * If none match the place, an <code>{@link Optional}</code>.empty() is returned.
     */
    public Optional<Restaurant> findRestaurantByPlace(String place) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.createQuery("from Restaurant rs where rs.place = :place", Restaurant.class)
                .setParameter("place", place)
                .getSingleResult());
    }

    /**
     * Resturns the List of <code>{@link Restaurant}</code>s that are associated with a meal with the given id.
     * @param id the id of the <code>{@link Meal}</code> to find all <code>{@link Restaurant}</code>s for.
     * @return Resturns the List of <code>{@link Restaurant}</code>s that are associated with a meal with the given id.
     */
    public List<Restaurant> findMealRestaurants(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Meal.restaurants m where m.id = :id", Restaurant.class)
                .setParameter("id", id)
                .getResultList();
    }


	/**
	 * Returns an <code>{@link Optional}</code><<code>{@link Restaurant}</code>> with the given address.
	 * @param address the address associated with the desired <code>{@link Restaurant}</code>
	 * @return an <code>{@link Optional}</code><<code>{@link Restaurant}</code>> with the given address.
	 * 			If none match the address, an <code>{@link Optional}</code>.empty() is returned.
	 */
	public Optional<Restaurant> findRestaurantByAddress(String address) {
		Session session = sessionFactory.getCurrentSession();
		return Optional.ofNullable(session.createQuery("from Restaurant rs where rs.address = :address", Restaurant.class)
				.setParameter("address", address)
				.getSingleResult());
	}

    /**
     * Saves a given <code>{@link Restaurant}</code> to the database.
     * @param restaurant the <code>{@link Restaurant}</code> to save to the database.
     * @return tne saved Restaurant. Returns Optional of null if unsuccessful.
     */
    public Optional<Restaurant> saveRestaurant(Restaurant restaurant) {
        Session session = sessionFactory.getCurrentSession();
        session.save(restaurant);
        return Optional.of(restaurant);

    }

}
