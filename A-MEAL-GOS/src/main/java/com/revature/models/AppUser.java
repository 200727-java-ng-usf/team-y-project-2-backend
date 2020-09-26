package com.revature.models;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Objects;


/**
 * Defines the basic Application user, with facets such as:
 * an id
 * username
 * the hash of their password
 * the salt of their password
 * email
 */
@Entity
@Table(name = "amg_users", schema = "amealgos")
public class AppUser {

	//region Fields
	/*
	 * GenerationTypes
	 * 		AUTO
	 * 			Default.
	 * 		IDENTITY
	 * 			Uses auto-incrementing value
	 * 		SEQUENCE
	 * 			Hibernate provides the SequenceStyleGenerator class.
	 * 				This class uses sequences if they're supported by the database, and switches to Table if not.
	 * 			Requires the use of the @GenericGenerator tag:
	 * 				@GenericGenerator(
						name = "sequence-generator",
						strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
						parameters = {
							@Parameter(name = "sequence_name", value = "user_sequence"),
							@Parameter(name = "initial_value", value = "4"),
							@Parameter(name = "increment_size", value = "1")
						}
					)
	 * 		TABLE
	 * 			Uses underlying database table that holds segments of identifier generation values.
	 * 				@GeneratedValue(strategy = GenerationType.TABLE,
						generator = "table-generator")
					@TableGenerator(name = "table-generator",
						table = "dep_ids",
						pkColumnName = "seq_id",
						valueColumnName = "seq_value")
	 * 			This method does not scale well and can negatively affect performance.
	 *
	 */
	@Id
	@Column(name="amg_user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="username", nullable = false)
	private String username;

	@Column(name="password_hash", nullable = false)
	private byte[] passwordHash;
	@Column(name="password_salt", nullable = false)
	private byte[] passwordSalt;
	@Column(name="email", unique = true, nullable = false)
	private String email;
	//endregion

	//region Constructors
	public AppUser(){
		super();
	}

	public AppUser(String username, byte[] passwordHash, byte[] passwordSalt, String email) {
		this();
		this.username = username;
		this.passwordHash = passwordHash;
		this.passwordSalt = passwordSalt;
		this.email = email;
	}

	public AppUser(String username, String email) {
		this();
		this.username = username;
		this.email = email;
	}
	public AppUser(String username, String password, String email) {
		this(username, email);
		saltAndHashPassword(password);
	}
	public AppUser(int id, String username, String password, String email) {
		this(username, email);
		saltAndHashPassword(password);
		this.id = id;
	}

	private AppUser(int id, String username, byte[] passwordHash, byte[] passwordSalt, String email) {
		this(username, email);
		this.id = id;
		this.passwordHash = passwordHash;
		this.passwordSalt = passwordSalt;
	}

	public AppUser(AppUser user){
		this(user.id, user.username, user.passwordHash, user.passwordSalt, user.email);
	}
	//endregion

	//region Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(byte[] passwordHash) {
		this.passwordHash = passwordHash;
	}

	public byte[] getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(byte[] passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public byte[] getString() {
		return passwordHash;
	}

	public void setString(byte[] password) {
		this.passwordHash = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	//endregion

	//region Methods

	/**
	 * Processes a given <code>{@link String}</code> into a password salt and hash.
	 * @param password the <code>{@link String}</code> to salt and hash
	 * @return true if the process was successful
	 */
	public boolean saltAndHashPassword(String password){
		if(password == null || password.trim().equals("")){
			passwordHash = new byte[0];
			passwordSalt = new byte[0];
			return false;
		}
		try {
			SecureRandom random = new SecureRandom();
			passwordSalt = new byte[256];
			random.nextBytes(passwordSalt);
			int iterationCount = 65536;
			int keyLength = 128;
			char[] pca = password.toCharArray();
			KeySpec spec = new PBEKeySpec(pca, passwordSalt, iterationCount, keyLength);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			passwordHash = factory.generateSecret(spec).getEncoded();
			return true;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Validates a given password against the given hash and salt. If true, the password matches this users' account.
	 * @param password the <code>{@link String}</code> to check
	 * @param hash the <code>byte[]</code> of the password hash
	 * @param salt the <code>byte[]</code> of the password salt
	 * @return true if the password matches the salt and hash.
	 */
	public boolean validatePassword(String password, byte[] hash, byte[] salt){
		try{
			int iterationCount = 65536;
			int keyLength = 128;
			char[] pca = password.toCharArray();
			KeySpec spec = new PBEKeySpec(pca, salt, iterationCount, keyLength);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return Arrays.equals(hash, factory.generateSecret(spec).getEncoded());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//endregion

	//region OverRidden Methods

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AppUser appUser = (AppUser) o;
		return id == appUser.id &&
				Objects.equals(username, appUser.username) &&
				Arrays.equals(passwordHash, appUser.passwordHash) &&
				Arrays.equals(passwordSalt, appUser.passwordSalt) &&
				Objects.equals(email, appUser.email);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(id, username, email);
		result = 31 * result + Arrays.hashCode(passwordHash);
		result = 31 * result + Arrays.hashCode(passwordSalt);
		return result;
	}

	@Override
	public String toString() {
		return "AppUser{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				'}';
	}

	public String toString(boolean getPasswordData) {
		if(!getPasswordData) return toString();
		return "AppUser{" +
				"id=" + id +
				", username='" + username + '\'' +
				", passwordHash=" + Arrays.toString(passwordHash) +
				", passwordSalt=" + Arrays.toString(passwordSalt) +
				", email='" + email + '\'' +
				'}';
	}

	//endregion
}
