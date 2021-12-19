package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetUserRes getUser(int userIdx){
        String getUserQuery = "select * from user where idx = ? and status = 'N'";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("idx"),
                        rs.getString("id"),
                        rs.getString("nickname"),
                        rs.getInt("level"),
                        rs.getString("imgUrl"),
                        rs.getString("authorName"),
                        rs.getString("prologTitle")),
                getUserParams);
    }
    

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into user (id, password, nickname) values (?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getId(), postUserReq.getPassword(), postUserReq.getNickname()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkId(String id){
        String Query = "select exists(select nickname from user where id = ? and status = 'N')";
        return this.jdbcTemplate.queryForObject(Query,
                int.class,
                id);
    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update user set nickname = ? where idx = ?";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getNickname(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select idx, password from user where id = ?";
        String getPwdParams = postLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("idx"),
                        rs.getString("password")),
                getPwdParams
                );

    }



}
