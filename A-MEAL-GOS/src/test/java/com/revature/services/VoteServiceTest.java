package com.revature.services;

import com.revature.daos.MealDao;
import com.revature.daos.RestaurantDao;
import com.revature.daos.VoteDao;
import com.revature.daos.VoteDao;
import com.revature.exceptions.AmealgoException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.*;
import com.revature.models.Vote;
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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.revature.*")
@ContextConfiguration(classes = ApplicationConfig.class)
public class VoteServiceTest {

    @Autowired
    private VoteService sut;
    private VoteDao mopitory;
    private List<Vote> mockVotes;
    private Vote vote1;
    private Vote vote2;
    private Vote vote3;
    private Vote vote4;
    private Vote vote5;
    private List<Restaurant> mockRestaurants;
    private Restaurant rest1;
    private Restaurant rest2;
    private Restaurant rest3;
    private Restaurant rest4;
    private Restaurant rest5;
    private List<Meal> mockMeals;
    private Meal meal1;
    private Meal meal2;
    private Meal meal3;
    private Meal meal4;
    private Meal meal5;
    private long count = 1;

    @Before
    public void setUp() throws Exception {
        mopitory = Mockito.mock(VoteDao.class);
        sut = new VoteService(mopitory);
        count = 1;
        mockVotes = new ArrayList<>();
        vote1 = setupVote(vote1);
        vote2 = setupVote(vote2);
        vote3 = setupVote(vote3);
        vote4 = setupVote(vote4);
        vote5 = setupVote(vote5);
        mockVotes.add(vote1);
        mockVotes.add(vote2);
        mockVotes.add(vote3);
        mockVotes.add(vote4);
        mockVotes.add(vote5);
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

    private Vote setupVote(Vote u) {
        u = Mockito.mock(Vote.class);
        when(u.getId()).thenReturn((int) count);
        count++;
        return u;
    }

    private Meal setupMeal(Meal m) {
        m = Mockito.mock(Meal.class);
        when(m.getId()).thenReturn((int) count);
        count++;
        return m;
    }

    @After
    public void tearDown() throws Exception {
        sut = null;
        mopitory = null;
        mockVotes = null;
        count = 1;
        vote1 = null;
        vote2 = null;
        vote3 = null;
        vote4 = null;
        vote5 = null;
    }

    @Test(expected = ResourceNotFoundException.class)
    public void notGetAllVote() throws ResourceNotFoundException {
        sut.getAllVotes();
    }

    @Test
    public void getAllVotes() throws ResourceNotFoundException {
        when(mopitory.findAll()).thenReturn(mockVotes);
        assertArrayEquals(mockVotes.toArray(), sut.getAllVotes().toArray());
    }

    @Test(expected = InvalidRequestException.class)
    public void getInvalidVoteById() throws InvalidRequestException, ResourceNotFoundException {
        sut.getVoteById(-1);
    }

    @Test(expected = AmealgoException.class)
    public void getNoVoteById() throws InvalidRequestException, ResourceNotFoundException {
        sut.getVoteById(1);
    }

    @Test
    public void getVoteById() throws InvalidRequestException, ResourceNotFoundException {
        when(mopitory.findById(1)).thenReturn(Optional.of(vote1));
        assertEquals(vote1, sut.getVoteById(1));
    }

    @Test
    public void getVoteByMeal() {
        when(mopitory.findVotesByMeal(meal1)).thenReturn(mockVotes);
        assertEquals(mockVotes, sut.getVotesByMeal(meal1));
    }

    @Test
    public void deleteVoteId() {
        when(mopitory.findById(1)).thenReturn(Optional.of(vote1));
        assertFalse( sut.deleteVoteById(1));
    }

    @Test
    public void newVote() {
        when(mopitory.save(vote1)).thenReturn(Optional.ofNullable(vote1));
        assertEquals(vote1, sut.createVote(vote1));
    }
}
