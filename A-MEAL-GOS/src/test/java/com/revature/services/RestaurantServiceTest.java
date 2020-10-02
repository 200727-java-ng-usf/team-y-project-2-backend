package com.revature.services;

import com.revature.daos.RestaurantDao;
import com.revature.exceptions.AmealgoException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Restaurant;
import com.revature.util.ApplicationConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.revature.*")
@ContextConfiguration(classes = ApplicationConfig.class)
public class RestaurantServiceTest {
	@Autowired
	private RestaurantService sut;
	private RestaurantDao mopitory;
	private List<Restaurant> maurants;
	private Restaurant r1;
	private Restaurant r2;
	private Restaurant r3;
	private Restaurant r4;
	private Restaurant r5;
	private long count = 1;

	@Before
	public void setUp() throws Exception {
		mopitory = Mockito.mock(RestaurantDao.class);
		sut = new RestaurantService(mopitory);
		count = 1;
		maurants = new ArrayList<>();
		r1 = setupRestaurant(r1,count);
		r2 = setupRestaurant(r2,count);
		r3 = setupRestaurant(r3,count);
		r4 = setupRestaurant(r4,count);
		r5 = setupRestaurant(r5,count);
		maurants.add(r1);
		maurants.add(r2);
		maurants.add(r3);
		maurants.add(r4);
		maurants.add(r5);
	}

	private Restaurant setupRestaurant(Restaurant r, long i){
		r = Mockito.mock(Restaurant.class);
		when(r.getId()).thenReturn((int)i);
		when(r.getPlace()).thenReturn("place"+i);
		when(r.getName()).thenReturn("name"+i);
		when(r.getAddress()).thenReturn("address"+i);
		count++;
		return r;
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
		mopitory = null;
		maurants = null;
		count = 1;
		r1 = null;
		r2 = null;
		r3 = null;
		r4 = null;
		r5 = null;

	}

	@Test
	public void getAllRestaurants() throws ResourceNotFoundException {
		when(mopitory.findAll()).thenReturn(maurants);
		assertArrayEquals(maurants.toArray(), sut.getAllRestaurant().toArray());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void getExceptionAllRestaurants() throws ResourceNotFoundException {
		when(mopitory.findAll()).thenThrow(RuntimeException.class);
		sut.getAllRestaurant();
	}

	@Test(expected = ResourceNotFoundException.class)
	public void notGetAllRestaurants() throws ResourceNotFoundException {
		sut.getAllRestaurant();
	}

	@Test
	public void getMealRestaurants() throws ResourceNotFoundException {
		when(mopitory.findMealRestaurants(1)).thenReturn(maurants);
		assertArrayEquals(maurants.toArray(), sut.getMealRestaurants(1).toArray());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void getExceptionMealRestaurants() throws ResourceNotFoundException {
		when(mopitory.findMealRestaurants(1)).thenThrow(RuntimeException.class);
		sut.getMealRestaurants(1);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void notGetMealRestaurants() throws ResourceNotFoundException {
		sut.getMealRestaurants(1);
	}

	@Test
	public void getRestaurantById() {
		when(mopitory.findById(1)).thenReturn(Optional.of(r1));
		assertEquals(r1,sut.getRestaurantById(1));
	}

	@Test(expected = InvalidRequestException.class)
	public void getInvalidRestaurantById() {
		sut.getRestaurantById(-1);
	}

	@Test(expected = AmealgoException.class)
	public void getExceptionRestaurantById() {
		sut.getRestaurantById(1);
	}

	@Test
	public void getRestaurantByPlaceId() {
		when(mopitory.findRestaurantByPlace("place1")).thenReturn(Optional.of(r1));
		assertEquals(r1, sut.getRestaurantByPlaceId("place1"));
	}

	@Test(expected = InvalidRequestException.class)
	public void getRestaurantByNullPlaceId() {
		sut.getRestaurantByPlaceId(null);
	}

	@Test(expected = InvalidRequestException.class)
	public void getRestaurantByEmptyPlaceId() {
		sut.getRestaurantByPlaceId("");
	}

	@Test(expected = AmealgoException.class)
	public void getExceptionRestaurantByPlaceId() {
		sut.getRestaurantByPlaceId("place1");
	}

	@Test
	public void updateRestaurant() {
		when(mopitory.update(r1)).thenReturn(true);
		assertTrue(sut.updateRestaurant(r1));
	}

	@Test
	public void updateFailRestaurant() {
		assertFalse(sut.updateRestaurant(r1));
	}

	@Test
	public void deleteRestaurantById() {
		when(mopitory.deleteById(1)).thenReturn(true);
		assertTrue(sut.deleteRestaurantById(1));
	}

	@Test
	public void deleteFailRestaurantById() {
		assertFalse(sut.deleteRestaurantById(1));
	}

	@Test
	public void createRestaurant() {
		when(mopitory.saveRestaurant(r1)).thenReturn(Optional.of(r1));
		assertEquals(r1,sut.createRestaurant(r1));
	}

	@Test
	public void createRestaurants() {
		when(mopitory.saveRestaurant(any())).thenReturn(any());
		assertEquals(new HashSet<>(maurants), sut.createRestaurants(new HashSet<>(maurants)));
	}
}