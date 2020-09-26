package com.revature.daos;

import java.util.List;
import java.util.Optional;

/**
 * The base Interface for the Database operations
 * Create
 * Replace
 * Update
 * Delete
 * @param <T> The Type of Repository to operate upon.
 */
public interface CrudDao<T> {
	/**
	 * Saves the given Type T to the database in the appropriate table(s)
	 * @param t the Type {@link T} item to save.
	 */
	public abstract Optional<T> save(T t);

	/**
	 * Returns all entries of Type T
	 * @return a <code>{@link List}</code><<code>{@link T}</code>> of all entries of Type <code>{@link T}</code>
	 */
	public abstract List<T> findAll();

	/**
	 * Returns an <code>{@link Optional}</code> of type <code>{@link T}</code> that has the given id.
	 * @param id the int id of the desired item.
	 * @return an <code>{@link Optional}</code> of type <code>{@link T}</code> that has the given id.
	 */
	public abstract Optional<T> findById(int id);

	/**
	 * Overwrites existing entry of id t.id in the database with the changed information in t.
	 * @param t the updated information to save to the database.
	 * @return true if 1 or more rows in the database were updated.
	 */
	public abstract boolean update(T t);

	/**
	 * Deletes entry with the given id from the database.
	 * @param id the id of the item to be deleted.
	 * @return true if at least one item in the database is deleted
	 * 			ensure your id's are unique!
	 */
	public abstract boolean deleteById(int id);
}
