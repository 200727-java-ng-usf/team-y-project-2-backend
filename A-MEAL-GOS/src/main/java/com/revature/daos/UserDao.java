package com.revature.daos;

import com.revature.models.AppUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The <code>{@link AppUser}</code> Data Access Object
 * Implements <code>{@link CrudDao}</code><<code>{@link AppUser}</code>>
 */
@Repository
public class UserDao implements CrudDao<AppUser> {

	/**
	 * The <code>{@link SessionFactory}</code> sessionFactory instance.
	 */
	private SessionFactory sessionFactory;

	@Autowired
	public UserDao(SessionFactory factory){
		sessionFactory = factory;
	}
	/**
	 * Saves the given <code>{@link AppUser}</code> to the repository.
	 * @param appUser the <code>{@link AppUser}</code> to save to the repository
	 */
	@Override
	public Optional<AppUser> save(AppUser appUser) {
		Session session = sessionFactory.getCurrentSession();
		session.save(appUser);
		return Optional.of(appUser);
	}

	/**
	 * Returns all <code>{@link AppUser}</code>s in the repository in an <code>{@link ArrayList}</code><<code>{@link AppUser}</code>>
	 * @return an <code>{@link ArrayList}</code><<code>{@link AppUser}</code>> of all <code>{@link AppUser}</code>s in the repository.
	 */
	@Override
	public List<AppUser> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from AppUser", AppUser.class).getResultList();
	}

	/**
	 * Returns an <code>{@link AppUser}</code>s with the given id.
	 * @param id the id associated with the <code>{@link AppUser}</code>.
	 * @return an <code>{@link AppUser}</code>s with the given id.
	 * 			If no <code>{@link AppUser}</code> is found, returns null.
	 */
	@Override
	public Optional<AppUser> findById(int id) {
		return findUserById(id);
	}

	/**
	 * Returns true if a successful update occurs.
	 * @param appUser the <code>{@link AppUser}</code> to update.
	 * @return true if update was successful.
	 */
	@Override
	public boolean update(AppUser appUser) {
		Session session = sessionFactory.getCurrentSession();
		try{
//			AppUser user = session.load(AppUser.class, username);
			session.update(appUser);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
//		Transaction tx = null;
//		try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
//			tx = session.beginTransaction();
////			session.update(appUser);
//			String hql = "update AppUser au ";
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
//			if(appUser.getUsername() != null && !appUser.getUsername().trim().equals("")){
//				System.out.println("username present.");
//				hql += "au.username = :username, ";
//			}
//			if(appUser.getPasswordHash() != null && appUser.getPasswordHash().length > 0){
//				System.out.println("passwordHash present.");
//				hql += "au.passwordHash = :passwordHash, ";
//			}
//			if(appUser.getPasswordSalt() != null && appUser.getPasswordSalt().length > 0){
//				System.out.println("passwordSalt present.");
//				hql += "au.passwordSalt = :passwordSalt, ";
//			}
//			if(appUser.getEmail() != null && !appUser.getEmail().trim().equals("")){
//				System.out.println("email present.");
//				hql += "au.email = :email, ";
//			}
//			hql += "where au.id = :id ";
//			Query query = session.createQuery(hql);
//
//			if(appUser.getUsername() != null && !appUser.getUsername().trim().equals("")) {
//				query.setParameter("username", appUser.getUsername());
//			}
//			if(appUser.getPasswordHash() != null && appUser.getPasswordHash().length > 0) {
//				query.setParameter("passwordHash", appUser.getPasswordHash());
//			}
//			if(appUser.getPasswordSalt() != null && appUser.getPasswordSalt().length > 0) {
//				query.setParameter("passwordSalt", appUser.getPasswordSalt());
//			}
//			if(appUser.getEmail() != null && !appUser.getEmail().trim().equals("")) {
//				query.setParameter("email", appUser.getEmail());
//			}
//
//			query.setParameter("id", appUser.getId());
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
	 * Deletes an <code>{@link AppUser}</code> by the <code>{@link AppUser}</code>s id.
	 * @param id the id of the <code>{@link AppUser}</code> to delete.
	 * @return true if the deletion was successful.
	 */
	@Override
	public boolean deleteById(int id) {
		Session session = sessionFactory.getCurrentSession();
		try{
			AppUser user = session.load(AppUser.class, id);
			session.delete(user);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
//		Transaction tx = null;
//		try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
//			tx = session.beginTransaction();
//			Query query = session.createQuery("delete AppUser au where au.id = :id ")
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
	 * Deletes an <code>{@link AppUser}</code> by the <code>{@link AppUser}</code>s username.
	 * @param username the username of the <code>{@link AppUser}</code> to delete.
	 * @return true if the deletion was successful.
	 */
	public boolean deleteByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		try{
			AppUser user = session.load(AppUser.class, username);
			session.delete(user);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
//		Transaction tx = null;
//		try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
//			tx = session.beginTransaction();
//			Query query = session.createQuery("delete AppUser au where au.username = :username")
//					.setParameter("username", username);
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
	 * Returns an <code>{@link Optional}</code><<code>{@link AppUser}</code>> with the given username.
	 * @param username the username associated with the desired <code>{@link AppUser}</code>
	 * @return an <code>{@link Optional}</code><<code>{@link AppUser}</code>> with the given username.
	 * 			If none match the username, an <code>{@link Optional}</code>.empty() is returned.
	 */
	public Optional<AppUser> findUserByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		return Optional.of(session.get(AppUser.class, username)); // get returns null, load throws an error.
	}

	/**
	 * Returns an <code>{@link Optional}</code><<code>{@link AppUser}</code>> with the given email.
	 * @param email the email associated with the desired <code>{@link AppUser}</code>
	 * @return an <code>{@link Optional}</code><<code>{@link AppUser}</code>> with the given email.
	 * 			If none match the email, an <code>{@link Optional}</code>.empty() is returned.
	 */
	public Optional<AppUser> findUserByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		return Optional.of(session.get(AppUser.class, email)); // get returns null, load throws an error.
	}

	/**
	 * Returns an <code>{@link Optional}</code><<code>{@link AppUser}</code>> with the given id.
	 * @param id the id associated with the desired <code>{@link AppUser}</code>
	 * @return an <code>{@link Optional}</code><<code>{@link AppUser}</code>> with the given id.
	 * 			If none match the id, an <code>{@link Optional}</code>.empty() is returned.
	 */
	public Optional<AppUser> findUserById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return Optional.of(session.get(AppUser.class, id)); // get returns null, load throws an error.
	}
}
