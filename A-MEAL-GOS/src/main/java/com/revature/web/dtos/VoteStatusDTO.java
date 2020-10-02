package com.revature.web.dtos;


import java.util.Objects;

public class VoteStatusDTO {

    private int mealId;
    private int numberOfVotesCast;
    private int userVotingFinished;
    private int mealVotingFinished; // 0 = not finished, 1 = finished

    public VoteStatusDTO() {
    }


    public VoteStatusDTO(int numberOfVotesCast, int userVotingFinished, int mealVotingFinished) {
        this.numberOfVotesCast = numberOfVotesCast;
        this.userVotingFinished = userVotingFinished;
        this.mealVotingFinished = mealVotingFinished;
    }

    public VoteStatusDTO(int mealId, int numberOfVotesCast, int userVotingFinished, int mealVotingFinished) {
        this.mealId = mealId;
        this.numberOfVotesCast = numberOfVotesCast;
        this.userVotingFinished = userVotingFinished;
        this.mealVotingFinished = mealVotingFinished;
    }

    public int getNumberOfVotesCast() {
        return numberOfVotesCast;
    }

    public void setNumberOfVotesCast(int numberOfVotesCast) {
        this.numberOfVotesCast = numberOfVotesCast;
    }

    public int getUserVotingFinished() {
        return userVotingFinished;
    }

    public void setUserVotingFinished(int userVotingFinished) {
        this.userVotingFinished = userVotingFinished;
    }

    public int getMealVotingFinished() {
        return mealVotingFinished;
    }

    public void setMealVotingFinished(int mealVotingFinished) {
        this.mealVotingFinished = mealVotingFinished;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteStatusDTO that = (VoteStatusDTO) o;
        return numberOfVotesCast == that.numberOfVotesCast &&
                userVotingFinished == that.userVotingFinished &&
                mealVotingFinished == that.mealVotingFinished;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfVotesCast, userVotingFinished, mealVotingFinished);
    }

    @Override
    public String toString() {
        return "VoteStatusDTO{" +
                "numberOfVotesCast=" + numberOfVotesCast +
                ", userVotingFinished=" + userVotingFinished +
                ", mealVotingFinished=" + mealVotingFinished +
                '}';
    }
}


