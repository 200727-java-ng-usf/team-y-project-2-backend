package com.revature.web.dtos;

import java.util.Objects;

public class ResultDto {

    private long amg_vote;
    private String restaurant_name;
    private String address;

    public ResultDto() {
        super();
    }

    public ResultDto(long amg_vote, String restaurant_name, String address) {
        this.amg_vote = amg_vote;
        this.restaurant_name = restaurant_name;
        this.address = address;
    }

    public long getAmg_vote() {
        return amg_vote;
    }

    public void setAmg_vote(long amg_vote) {
        this.amg_vote = amg_vote;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultDto resultDto = (ResultDto) o;
        return amg_vote == resultDto.amg_vote &&
                Objects.equals(restaurant_name, resultDto.restaurant_name) &&
                Objects.equals(address, resultDto.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amg_vote, restaurant_name, address);
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "total=" + amg_vote +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
