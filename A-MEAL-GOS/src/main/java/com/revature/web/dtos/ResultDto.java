package com.revature.web.dtos;

import java.util.Objects;

public class ResultDto {

    private int total;
    private String restaurant_name;
    private String address;

    public ResultDto() {
        super();
    }

    public ResultDto(int total, String restaurant_name, String address) {
        this.total = total;
        this.restaurant_name = restaurant_name;
        this.address = address;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
        return total == resultDto.total &&
                Objects.equals(restaurant_name, resultDto.restaurant_name) &&
                Objects.equals(address, resultDto.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(total, restaurant_name, address);
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "total=" + total +
                ", restaurant_name='" + restaurant_name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
