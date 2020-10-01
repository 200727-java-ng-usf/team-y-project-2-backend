package com.revature.util;

import com.revature.web.dtos.ResultDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WinningVoteMapper implements RowMapper<ResultDto> {


    @Override
    public ResultDto mapRow(ResultSet resultSet, int i) throws SQLException {

        ResultDto resultDto = new ResultDto();

        resultDto.setAmg_vote(resultSet.getInt("total"));
        resultDto.setRestaurant_name(resultSet.getString("restaurant_name"));
        resultDto.setAddress(resultSet.getString("address"));

        return resultDto;

    }
}
