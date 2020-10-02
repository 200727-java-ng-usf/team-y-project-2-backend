package com.revature.services;

import com.revature.daos.MealDao;
import com.revature.exceptions.AmealgoException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.models.AppUser;
import com.revature.models.Meal;
import com.revature.models.Restaurant;
import com.revature.models.Role;
import com.revature.util.ApplicationConfig;
import com.revature.web.dtos.ResultDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.revature.*")
@ContextConfiguration(classes = ApplicationConfig.class)
public class MealServiceTest {
	@Autowired
	private MealService sut;
	private MealDao mopitory;
	private List<Meal> mockMeals;
	private Meal meal1;
	private Meal meal2;
	private Meal meal3;
	private Meal meal4;
	private Meal meal5;
	private Restaurant r1;
	private Restaurant r2;
	private Restaurant r3;
	private Restaurant r4;
	private Restaurant r5;
	private long count = 1;

	@Before
	public void setUp() throws Exception {
		mopitory = Mockito.mock(MealDao.class);
		sut = new MealService(mopitory);
		count = 1;
		mockMeals = new ArrayList<>();
		meal1 = setupMeal(meal1);
		meal2 = setupMeal(meal2);
		meal3 = setupMeal(meal3);
		meal4 = setupMeal(meal4);
		meal5 = setupMeal(meal5);
		mockMeals.add(meal1);
		mockMeals.add(meal2);
		mockMeals.add(meal3);
		mockMeals.add(meal4);
		mockMeals.add(meal5);
	}

	private Meal setupMeal(Meal m){
		m = Mockito.mock(Meal.class);
		r1 = setupRestaurant(r1, 5*count);
		r2 = setupRestaurant(r2, 5*count + 1);
		r3 = setupRestaurant(r3, 5*count + 2);
		r4 = setupRestaurant(r4, 5*count + 3);
		r5 = setupRestaurant(r5, 5*count + 4);
		Set<Restaurant> restaurants = new HashSet<>();
		restaurants.add(r1);
		restaurants.add(r2);
		restaurants.add(r3);
		restaurants.add(r4);
		restaurants.add(r5);
		AppUser u1 = null;
		AppUser u2 = null;
		AppUser u3 = null;
		AppUser u4 = null;
		AppUser u5 = null;
		u1 = setupUser(u1, 5*count);
		u2 = setupUser(u2, 5*count + 1);
		u3 = setupUser(u3, 5*count + 2);
		u4 = setupUser(u4, 5*count + 3);
		u5 = setupUser(u5, 5*count + 4);
		Set<AppUser> users = new HashSet<>();
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);
		users.add(u5);
		when(m.getId()).thenReturn((int) count);
		when(m.getNumVotes()).thenReturn((int)count);
		when(m.getMealName()).thenReturn("meal"+count);
		when(m.getFinalRestaurant()).thenReturn(r1);
		when(m.getRestaurants()).thenReturn(restaurants);
		when(m.getUsersInMeal()).thenReturn(users);
		when(m.getUsersFinishedVoting()).thenReturn(users);
		count++;
		return m;
	}

	private Restaurant setupRestaurant(Restaurant r, long i){
		r = Mockito.mock(Restaurant.class);
		when(r.getId()).thenReturn((int)i);
		when(r.getPlace()).thenReturn("place"+i);
		when(r.getName()).thenReturn("name"+i);
		when(r.getAddress()).thenReturn("address"+i);
		return r;
	}

	private AppUser setupUser(AppUser u, long i) {
		u = Mockito.mock(AppUser.class);
		when(u.getId()).thenReturn((int) i);
		when(u.getUsername()).thenReturn("LookAtMe" + i);
		when(u.getPasswordHash()).thenReturn(new byte[10]);
		when(u.getPasswordSalt()).thenReturn(new byte[10]);
		when(u.getEmail()).thenReturn("mr.meseeks" + i + "@yessirree.org");
		when(u.getRole()).thenReturn(Role.BASIC_USER);
		return u;
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
		mopitory = null;
		mockMeals = null;
		count = 1;
		meal1 = null;
		meal2 = null;
		meal3 = null;
		meal4 = null;
		meal5 = null;

	}

	@Test
	public void getAllMeals() throws ResourceNotFoundException {
		when(mopitory.findAll()).thenReturn(mockMeals);
		assertArrayEquals(mockMeals.toArray(), sut.getAllMeals().toArray());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void getExceptionAllMeals() throws ResourceNotFoundException {
		when(mopitory.findAll()).thenThrow(RuntimeException.class);
		sut.getAllMeals();
	}

	@Test(expected = ResourceNotFoundException.class)
	public void notGetAllMeals() throws ResourceNotFoundException {
		sut.getAllMeals();
	}

	@Test
	public void getMealById() {
		when(mopitory.findById(1)).thenReturn(Optional.of(meal1));
		assertEquals(meal1, sut.getMealById(1));
	}

	@Test(expected = InvalidRequestException.class)
	public void invalidGetMealById() {
		sut.getMealById(-1);
	}

	@Test(expected = AmealgoException.class)
	public void amealgoGetMealById() {
		sut.getMealById(1);
	}

	@Test
	public void create() {
		sut.create(meal1);
	}

	@Test(expected = InvalidRequestException.class)
	public void invalidCreate() {
		sut.create(null);
	}

	@Test(expected = ResourcePersistenceException.class)
	public void persistenceCreate() {
		when(mopitory.save(meal1)).thenThrow(RuntimeException.class);
		sut.create(meal1);
	}

	@Test
	public void updateMeal() {
		when(mopitory.update(meal1)).thenReturn(true);
		assertTrue(sut.updateMeal(meal1));
	}

	@Test
	public void badUpdateMeal() {
		assertFalse(sut.updateMeal(meal1));
	}

	@Test
	public void deleteMealById() {
		when(mopitory.deleteById(1)).thenReturn(true);
		assertTrue(sut.deleteMealById(1));
	}

	@Test
	public void badDeleteMealById() {
		assertFalse(sut.deleteMealById(1));
	}

	@Test
	public void deleteMealByMealname() {
		when(mopitory.deleteByMealname("fingerLickingGood")).thenReturn(true);
		assertTrue(sut.deleteMealByMealname("fingerLickingGood"));
	}


	@Test
	public void badDeleteMealByMealname() {
		assertFalse(sut.deleteMealByMealname("fingerLickingGood"));
	}

	@Test
	public void isMealValid() {
		assertTrue(sut.isMealValid(meal1));
	}

	@Test
	public void isNullMealValid() {
		assertFalse(sut.isMealValid(null));
	}

	@Test
	public void isNullNameMealValid() {
		when(meal1.getMealName()).thenReturn(null);
		assertFalse(sut.isMealValid(meal1));
	}

	@Test
	public void isEmptyNameMealValid() {
		when(meal1.getMealName()).thenReturn("");
		assertFalse(sut.isMealValid(meal1));
	}

	@Test
	public void isSpaceyNameMealValid() {
		when(meal1.getMealName()).thenReturn("                             ");
		assertFalse(sut.isMealValid(meal1));
	}

	@Test
	public void getWinningMeal(){
		ResultDto res = Mockito.mock(ResultDto.class);
		when(mopitory.findWinningRestaurant(1)).thenReturn(res);
		assertEquals(res,sut.getWinningMeal(1));
	}

	@Test
	public void addToMeal() {
		AppUser u = null;
		u = setupUser(u, 42);
		assertEquals(u,sut.addToMeal(u,meal1));
	}

	@Test
	public void addToFinishedVoting() {
		AppUser u = null;
		u = setupUser(u, 42);
		assertEquals(u,sut.addToFinishedVoting(u,meal1));
	}
}