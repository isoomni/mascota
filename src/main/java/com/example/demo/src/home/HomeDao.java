package com.example.demo.src.home;

import com.example.demo.src.home.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class HomeDao {

    private JdbcTemplate jdbcTemplate;
    private GetHomeNicknameRes getHomeNicknameRes;
    private List<GetHomePetsRes> getHomePetsRes;
    private GetHomeLevelRes getHomeLevelRes;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public GetHomeRes getHome(int userIdx){

        // 작가명
        String getHomeQuery1 = "select nickname from mascota.user as u where u.idx = ?;";
        // 반려동물 리스트
        String getHomeQuery2 = "select p.name from mascota.pet as p\n" +
                "where p.user_id = ?;";
        // 레벨
        String getHomeQuery3 = "select u.level from mascota.user as u where u.idx = ?";


        int getHomeParams = userIdx;

        return new GetHomeRes(

                getHomeNicknameRes = this.jdbcTemplate.queryForObject(getHomeQuery1,
                        (rs, rowNum) -> new GetHomeNicknameRes(
                                rs.getString("nickname")
                        ), getHomeParams),
                getHomePetsRes = this.jdbcTemplate.query(getHomeQuery2,
                        (rs, rowNum) -> new GetHomePetsRes(
                                rs.getString("name")
                        ),getHomeParams),
                getHomeLevelRes = this.jdbcTemplate.queryForObject(getHomeQuery3,
                        (rs, rowNum) -> new GetHomeLevelRes(
                                rs.getString("level")
                        ), getHomeParams
                ));
    }


}
