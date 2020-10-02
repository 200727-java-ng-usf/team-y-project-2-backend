package com.revature.services;

import com.revature.daos.VoteDao;
import com.revature.exceptions.AmealgoException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.models.AppUser;
import com.revature.models.Meal;
import com.revature.models.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Models all services and operations that might apply to <code>{@link Vote}</code>s.
 */
@Service
public class VoteService {
	/**
	 * An <code>{@link Vote}</code> Data Access Object Instance.
	 */
	private VoteDao voteDao;

	@Autowired
	public VoteService(VoteDao dao){
		this.voteDao = dao;
	}


	//region Methods

	/**
	 * Returns all restaurants registered within the database.
	 * @return a Set of <code>{@link Vote}</code>s that have been registered and saved to the database
	 */
	@Transactional(readOnly = true)
	public List<Vote> getAllVotes() throws ResourceNotFoundException {
		List<Vote> votes = new ArrayList<>();
		try{
			votes = voteDao.findAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(votes.isEmpty()){
			throw new ResourceNotFoundException();
		}
		return votes;
	}

	/**
	 * Returns the first <code>{@link Vote}</code> found with the given id.
	 * @param id the int id to search by
	 * @return the first <code>{@link Vote}</code> found with the given id.
	 */
	@Transactional(readOnly = true)
	public Vote getVoteById(int id) throws ResourceNotFoundException {
		if(id <= 0){
			throw new InvalidRequestException("The provided id cannot be less than or equal to zero.");
		}

		try{
			return voteDao.findById(id).orElseThrow(ResourceNotFoundException::new);
		} catch(Exception e) {
			throw new AmealgoException(e);
		}
	}

	/**
	 * Returns the first <code>{@link Vote}</code> found with the given username.
	 * @param mealName the String name to search by.
	 * @return the first <code>{@link Vote}</code> found with the given name.
	 */
	@Transactional(readOnly = true)
	public List<Vote> getVotesByMeal(Meal mealName){
		List<Vote> votes = new ArrayList<>();
		if(mealName == null){
			throw new InvalidRequestException("Meal name cannot be null or empty.");
		}
		try{
			votes =  voteDao.findVotesByMeal(mealName);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(votes.isEmpty()){
			throw new ResourceNotFoundException();
		}
		return votes;
	}


	/**
	 * This method updates the records of a <code>{@link Vote}</code>
	 * that exists on the database with the local record.
	 * @param restaurant the <code>{@link Vote}</code> to update.
	 * @return returns true if the update was successful, false otherwise.
	 */
	@Transactional(readOnly = false)
	public boolean updateVote(Vote restaurant){
		return voteDao.update(restaurant);
	}

	/**
	 * This method deletes a <code>{@link Vote}</code> with the given id from
	 * the the database, along with any records only pertinent to them.
	 * @param id the id of the <code>{@link Vote}</code>.
	 * @return returns true if the deletion was successful, false if otherwise.
	 * 		If there was no such user, returns true.
	 */
	@Transactional(readOnly = false)
	public boolean deleteVoteById(int id){
		return voteDao.deleteById(id);
	}


	@Transactional(readOnly = false)
	public Vote createVote(Vote vote){

		if (vote.getVote() == 0 || vote.getVote() == 1) {
			return voteDao.save(vote).get();
		}
		throw new ResourcePersistenceException("Vote must be positive or negative!");
	}

	public int getNumberOfVotesCast(AppUser user) {

		List<Vote> votes = voteDao.findCastVotesByUser(user);

		return votes.size();

	}

	//endregion
}
