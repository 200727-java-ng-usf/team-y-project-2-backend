package com.revature.services;

import com.revature.daos.UserDao;
import com.revature.exceptions.*;
import com.revature.models.AppUser;
import com.revature.web.dtos.Credentials;
import com.revature.web.dtos.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Models all services and operations that might apply to <code>{@link AppUser}</code>s.
 */
@Service
public class UserService {
	/**
	 * An <code>{@link AppUser}</code> Data Access Object Instance.
	 */
	private UserDao userDao;

	@Autowired
	public UserService(UserDao userDao){
		this.userDao = userDao;
	}

//region Constructors
//	public UserService(UserDao repo){
////		System.out.println("[LOG] - Instantiating " + this.getClass().getName());
//		userDao = repo;
//	}
	//endregion

	//region Methods

	/**
	 * Returns all users registered with the bank database.
	 * @return a Set of <code>{@link AppUser}</code>s that have been registered and saved to the bank database
	 */
	@Transactional(readOnly = true)
	public List<AppUser> getAllUser() throws ResourceNotFoundException {
		List<AppUser> users = new ArrayList<>();
		try{
			users = userDao.findAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(users.isEmpty()){
			throw new ResourceNotFoundException();
		}
		return users;
	}

	/**
	 * Returns the first <code>{@link AppUser}</code> found with the given id.
	 * @param id the int id to search by
	 * @return the first <code>{@link AppUser}</code> found with the given id.
	 */
	@Transactional(readOnly = true)
	public AppUser getUserById(int id) throws ResourceNotFoundException {
		if(id <= 0){
			throw new InvalidRequestException("The provided id cannot be less than or equal to zero.");
		}

		try{
			return userDao.findUserById(id)
					.orElseThrow(ResourceNotFoundException::new);
		} catch(Exception e) {
			throw new AmealgoException(e);
		}
	}

	/**
	 * Returns the first <code>{@link AppUser}</code> found with the given username.
	 * @param username the String username to search by.
	 * @return the first <code>{@link AppUser}</code> found with the given username.
	 */
	@Transactional(readOnly = true)
	public AppUser getUserByUsername(String username){
		if(username == null || username.equals("")){
			throw new InvalidRequestException("Username cannot be null or empty.");
		}
		try{
			return userDao.findUserByUsername(username)
					.orElseThrow(ResourceNotFoundException::new);
		} catch(Exception e) {
			throw new AmealgoException(e);
		}
	}

	/**
	 * This method authenticates a <code>{@link AppUser}</code>  that already exists within the database.
	 * with the given credentials with the database. If a user already exists with the credentials
	 * or a user does not exist with the credentials or any credential is either null or empty,
	 * then the method will not authenticate.
	 * @param credentials the <code>{@link Credentials}</code> to authenticate.
	 */
	@Transactional
	public Principal authenticate(Credentials credentials) throws AuthenticationException{
		// Validate that the provided username and password are not non-values
		if(credentials == null || credentials.getUsername() == null || credentials.getUsername().trim().equals("")
				|| credentials.getPassword() == null || credentials.getPassword().trim().equals("")){
			throw new InvalidRequestException("Invalid credential values provided");
		}

		try{
			AppUser user = userDao.findUserByUsername(credentials.getUsername())
					.orElseThrow(AuthenticationException::new);
			if(!user.validatePassword(
					credentials.getPassword(),
					user.getPasswordHash(),
					user.getPasswordSalt())){
				throw new AuthenticationException("Incorrect Password.");
			}
			return new Principal(user);
//			return (user);
		} catch(Exception e) {
			throw new AmealgoException(e);
		}
	}

	/**
	 * This method registers a new <code>{@link AppUser}</code> into the database.
	 * @param newUser the <code>{@link AppUser}</code> to store/register in the database.
	 */
	@Transactional
	public AppUser register(AppUser newUser){

		if(!isUserValid(newUser)){
			throw new InvalidRequestException("Invalid user field values provided during registration!");
		}

		if(!isEmailAvailable(newUser.getEmail())){
			throw new ResourcePersistenceException("The provided email is already taken!");
		}

		try{
			userDao.save(newUser);
		} catch(Exception e) {
			throw new ResourcePersistenceException("Could not persist new AppUser!");
		}


		return newUser;
	}

	/**
	 * This method updates the records of a <code>{@link AppUser}</code>
	 * that exists on the database with the local record.
	 * @param user the <code>{@link AppUser}</code> to update.
	 * @return returns true if the update was successful, false otherwise.
	 */
	@Transactional(readOnly = false)
	public boolean updateUser(AppUser user){
		return userDao.update(user);
	}

	/**
	 * This method deletes a <code>{@link AppUser}</code> with the given id from
	 * the the database, along with any records only pertinent to them.
	 * @param id the id of the <code>{@link AppUser}</code>.
	 * @return returns true if the deletion was successful, false if otherwise.
	 * 		If there was no such user, returns true.
	 */
	@Transactional(readOnly = false)
	public boolean deleteUserById(int id){
		return userDao.deleteById(id);
	}

	/**
	 * This method deletes a <code>{@link AppUser}</code> with the given id from
	 * the the database, along with any records only pertinent to them.
	 * @param username the id of the <code>{@link AppUser}</code>.
	 * @return returns true if the deletion was successful, false if otherwise.
	 * 		If there was no such user, returns true.
	 */
	@Transactional(readOnly = false)
	public boolean deleteUserByUsername(String username){
		return userDao.deleteByUsername(username);
	}

	/**
	 * Returns true if no <code>{@link AppUser}</code> in the database has the given username.
	 * @param username the username to find the availability of
	 * @return true if no <code>{@link AppUser}</code> in the database has the given username.
	 */
	@Transactional(readOnly = true)
	public boolean isUsernameAvailable(String username) {
		if(username == null || username.equals("")){
			throw new InvalidRequestException("Username cannot be null or empty!");
		}
		try{
			return userDao.findUserByUsername(username).orElse(null) == null;
		} catch (Exception e) {
			throw new AmealgoException(e);
		}
	}

	/**
	 * Returns true if no <code>{@link AppUser}</code> in the database has the given email.
	 * @param email the email to find the availability of
	 * @return true if no <code>{@link AppUser}</code> in the database has the given email.
	 */
	@Transactional(readOnly = true)
	public boolean isEmailAvailable(String email) {
		if(email == null || email.equals("")){
			throw new InvalidRequestException("Email cannot be null or empty!");
		}
		try {
			return !userDao.findUserByEmail(email).isPresent();
		} catch (NoResultException nre) {
			return true;
		} catch (Exception e) {
			throw new AmealgoException(e);
		}
	}

	/**
	 * Validates that the given <code>{@link AppUser}</code> and its fields are
	 * valid (not null or empty strings). Does not perform validation on id or Role fields.
	 *
	 * @param user the <code>{@link AppUser}</code> to validate.
	 * @return true if the <code>{@link AppUser}</code> is valid.
	 */
	public boolean isUserValid(AppUser user){
		if(user == null) return false;
		if(user.getUsername() == null || user.getUsername().trim().equals("")) return false;
		if(user.getPasswordHash() == null || user.getPasswordHash().length == 0) return false;
		if(user.getEmail() == null || user.getEmail().trim().equals("")) return false;

		return true;
	}
	//endregion
}
