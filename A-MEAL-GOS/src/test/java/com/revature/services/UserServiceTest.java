package com.revature.services;

import com.revature.daos.UserDao;
import com.revature.exceptions.*;
import com.revature.web.dtos.Credentials;
import com.revature.models.AppUser;
import com.revature.util.ApplicationConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class UserServiceTest {
	@Autowired
	private UserService sut;
	private UserDao mopitory;
	private List<AppUser> mockUsers;
	private AppUser user1;
	private AppUser user2;
	private AppUser user3;
	private AppUser user4;
	private AppUser user5;
	private long count = 1;

	@Before
	public void setUp() throws Exception {
		mopitory = Mockito.mock(UserDao.class);
		sut = new UserService(mopitory);
//		sut.setUserDao(mopitory);
		count = 1;
		mockUsers = new ArrayList<>();
		user1 = setupUser(user1);
		user2 = setupUser(user2);
		user3 = setupUser(user3);
		user4 = setupUser(user4);
		user5 = setupUser(user5);
		mockUsers.add(user1);
		mockUsers.add(user2);
		mockUsers.add(user3);
		mockUsers.add(user4);
		mockUsers.add(user5);
	}

	private AppUser setupUser(AppUser u) {
		u = Mockito.mock(AppUser.class);
		when(u.getId()).thenReturn((int) count++);
		when(u.getUsername()).thenReturn("LookAtMe" + count);
		when(u.getPasswordHash()).thenReturn(new byte[10]);
		when(u.getPasswordSalt()).thenReturn(new byte[10]);
		when(u.getEmail()).thenReturn("mr.meseeks" + count + "@yessirree.org");
		return u;
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
		mopitory = null;
		mockUsers = null;
		count = 1;
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
		user5 = null;
	}

	@Test(expected = ResourceNotFoundException.class)
	public void notGetAllUser() throws ResourceNotFoundException {
		sut.getAllUser();
	}

	@Test
	public void getAllUser() throws ResourceNotFoundException {
		when(mopitory.findAll()).thenReturn(mockUsers);
		assertArrayEquals(mockUsers.toArray(), sut.getAllUser().toArray());
	}

	@Test(expected = InvalidRequestException.class)
	public void getInvalidUserById() throws InvalidRequestException, ResourceNotFoundException {
		sut.getUserById(-1);
	}

	@Test(expected = AmealgoException.class)
	public void getNoUserById() throws InvalidRequestException, ResourceNotFoundException {
		sut.getUserById(1);
	}

	@Test
	public void getUserById() throws InvalidRequestException, ResourceNotFoundException {
		when(mopitory.findUserById(1)).thenReturn(Optional.of(user1));
		assertEquals(user1, sut.getUserById(1));
	}

	@Test
	public void getUserByUsername() {
		when(mopitory.findUserByUsername("LookAtMe1")).thenReturn(Optional.of(user1));
		assertEquals(user1, sut.getUserByUsername("LookAtMe1"));
	}

	@Test(expected = AmealgoException.class)
	public void getNullUserByUsername() {
		when(mopitory.findUserByUsername("Jerry")).thenReturn(null);
		sut.getUserByUsername("LookAtMe1");
	}

	@Test
	public void authenticate() {
		Credentials creds = Mockito.mock(Credentials.class);
		when(creds.getUsername()).thenReturn("Mr.");
		when(creds.getPassword()).thenReturn("password");
		when(mopitory.findUserByUsername(creds.getUsername())).thenReturn(Optional.of(user1));
		when(user1.validatePassword(anyString(), any(), any())).thenReturn(true);
		assertEquals(user1, sut.authenticate(creds));
	}

	@Test(expected = InvalidRequestException.class)
	public void authenticateNullUsername() {
		Credentials creds = Mockito.mock(Credentials.class);
		when(creds.getUsername()).thenReturn(null);
		when(creds.getPassword()).thenReturn("password");
		when(mopitory.findUserByUsername(creds.getUsername())).thenReturn(Optional.of(user1));
		when(user1.validatePassword(anyString(), any(), any())).thenReturn(true);
		assertEquals(user1, sut.authenticate(creds));
	}

	@Test(expected = InvalidRequestException.class)
	public void authenticateEmptyUsername() {
		Credentials creds = Mockito.mock(Credentials.class);
		when(creds.getUsername()).thenReturn("");
		when(creds.getPassword()).thenReturn("password");
		when(mopitory.findUserByUsername(creds.getUsername())).thenReturn(Optional.of(user1));
		when(user1.validatePassword(anyString(), any(), any())).thenReturn(true);
		assertEquals(user1, sut.authenticate(creds));
	}

	@Test(expected = InvalidRequestException.class)
	public void authenticateNullPassword() {
		Credentials creds = Mockito.mock(Credentials.class);
		when(creds.getUsername()).thenReturn("Mr.");
		when(creds.getPassword()).thenReturn(null);
		when(mopitory.findUserByUsername(creds.getUsername())).thenReturn(Optional.of(user1));
		when(user1.validatePassword(anyString(), any(), any())).thenReturn(true);
		assertEquals(user1, sut.authenticate(creds));
	}

	@Test(expected = InvalidRequestException.class)
	public void authenticateEmptyPassword() {
		Credentials creds = Mockito.mock(Credentials.class);
		when(creds.getUsername()).thenReturn("Mr.");
		when(creds.getPassword()).thenReturn("");
		when(mopitory.findUserByUsername(creds.getUsername())).thenReturn(Optional.of(user1));
		when(user1.validatePassword(anyString(), any(), any())).thenReturn(true);
		assertEquals(user1, sut.authenticate(creds));
	}

	@Test(expected = AmealgoException.class)
	public void authenticateInvalidPassword() {
		Credentials creds = Mockito.mock(Credentials.class);
		when(creds.getUsername()).thenReturn("Mr.");
		when(creds.getPassword()).thenReturn("password");
		when(mopitory.findUserByUsername(creds.getUsername())).thenReturn(Optional.of(user1));
		when(user1.validatePassword(anyString(), any(), any())).thenReturn(false);
		assertEquals(user1, sut.authenticate(creds));
	}

	@Test
	public void register() {
		when(mopitory.findUserByUsername(user1.getUsername())).thenReturn(Optional.empty());
		sut.register(user1);
	}

	@Test(expected = InvalidRequestException.class)
	public void registerInvalidUser() {
		when(mopitory.findUserByUsername(user1.getUsername())).thenReturn(Optional.empty());
		sut.register(null);
	}

	@Test(expected = ResourcePersistenceException.class)
	public void registerPreexistingUser() {
		when(mopitory.findUserByEmail(user1.getEmail())).thenReturn(Optional.of(user1));
		sut.register(user1);
	}

	@Test
	public void updateUser() {
		when(mopitory.update(user1)).thenReturn(true);
		assertTrue(sut.updateUser(user1));
	}

	@Test
	public void updateFalseUser() {
		when(user1.getUsername()).thenReturn("");
		when(mopitory.update(user1)).thenReturn(false);
		assertFalse(sut.updateUser(user1));
	}

	@Test
	public void deleteUserById() {
		when(mopitory.deleteById(1)).thenReturn(true);
		assertTrue(sut.deleteUserById(1));
	}

	@Test
	public void noDeleteUserById() {
		when(mopitory.deleteById(1)).thenReturn(false);
		assertFalse(sut.deleteUserById(1));
	}

	@Test
	public void deleteUserByUsername() {
		when(mopitory.deleteByUsername("LookAtMe1")).thenReturn(true);
		assertTrue(sut.deleteUserByUsername("LookAtMe1"));
	}

	@Test
	public void noDeleteUserByUsername() {
		when(mopitory.deleteByUsername("LookAtMe10")).thenReturn(false);
		assertFalse(sut.deleteUserByUsername("LookAtMe10"));
	}

	@Test
	public void isUsernameAvailable() {
		when(mopitory.findUserByUsername("LookAtMe10")).thenReturn(Optional.empty());
		assertTrue(sut.isUsernameAvailable("LookAtMe10"));
	}

	@Test
	public void isUsernameNotAvailable() {
		when(mopitory.findUserByUsername("LookAtMe1")).thenReturn(Optional.of(user1));
		assertFalse(sut.isUsernameAvailable("LookAtMe1"));
	}

	@Test
	public void isEmailAvailable() {
		when(mopitory.findUserByEmail("mr.meseeks100000@yessirree.org")).thenReturn(Optional.empty());
		assertTrue(sut.isEmailAvailable("mr.meseeks100000@yessirree.org"));
	}

	@Test
	public void isEmailNotAvailable() {
		when(mopitory.findUserByEmail("mr.meseeks1@yessirree.org")).thenReturn(Optional.of(user1));
		assertFalse(sut.isEmailAvailable("mr.meseeks1@yessirree.org"));
	}

	//TODO ALL CASES
	@Test
	public void isUserValid() {
		assertTrue(sut.isUserValid(user1));
	}


}