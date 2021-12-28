package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

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
                        rs.getString("prologTitle")),
                getUserParams);
    }
    

    public int createUser(PostLoginReq postUserReq){
        String createUserQuery = "insert into user (id, password) values (?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getId(), postUserReq.getPassword()};
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

    public int checkPet(int idx){
        String Query = "select exists(select name from pet where idx = ? and status = 'N')";
        return this.jdbcTemplate.queryForObject(Query,
                int.class,
                idx);
    }

    public int checkUser(int idx){
        String Query = "select exists(select nickname from user where idx = ? and status = 'N')";
        return this.jdbcTemplate.queryForObject(Query,
                int.class,
                idx);
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

    public int createPet(Pet pet, int userIdx){
        String Query = "insert into pet (userIdx, imgUrl, name, type, birth) values (?,?,?,?,?)";
        Object[] param = new Object[]{userIdx, pet.getImgUrl(), pet.getName(), pet.getType(), pet.getBirth()};

        return this.jdbcTemplate.update(Query,param);
    }

    public int updatePet(Pet pet, int petIdx){
        String Query = "update pet set ";
        if (pet.getName() != null){
            Query += "name = '" + pet.getName() + "', ";
        }

        if (pet.getImgUrl() != null){
            Query += "imgUrl = '" + pet.getName() + "', ";
        }

        if (pet.getType() != null){
            Query += "type = '" + pet.getType() + "', ";
        }

        if (pet.getBirth() != null){
            Query += "birth = '" + pet.getBirth() + "', ";
        }

        Query = Query.substring(0,Query.length()-2);
        Query += " where idx = ?";

        return this.jdbcTemplate.update(Query,petIdx);
    }

    public int deletePet(int petIdx){
        String Query = "update diary join mood on diary.idx = mood.diaryIdx join pet on pet.idx = mood.petIdx set pet.status = 'D', diary.status = 'D' where pet.idx = ?";

        return this.jdbcTemplate.update(Query,petIdx);
    }

    public List<Pet> getPetList(int userIdx){
        String Query = "select idx, name, imgUrl, type, birth from pet where userIdx = ? and status = 'N'";

        return this.jdbcTemplate.query(Query,
                (rs,rowNum) -> new Pet(
                        rs.getInt("idx"),
                        rs.getString("name"),
                        rs.getString("imgUrl"),
                        rs.getString("type"),
                        rs.getString("birth")),
                userIdx);
    }

    public int createBook(PostUserReq postUserReq, int userIdx){
        String Query = "update user set imgUrl = ?, nickname = ?, prologTitle = ? where idx = ?";
        Object[] param = new Object[]{postUserReq.getImgUrl(), postUserReq.getNickname(), postUserReq.getTitle(), userIdx};

        return this.jdbcTemplate.update(Query,param);
    }

    public Book getBook(int userIdx){
        String Query = "select nickname, user.imgUrl as userImg, prologTitle, " +
                "pet.idx, pet.name, pet.imgUrl, pet.type, pet.birth from user " +
                "left join pet on pet.userIdx = user.idx and pet.status = 'N' where user.idx = ?";
        ArrayList<Pet> petList = new ArrayList<Pet>();
        List<User> user = this.jdbcTemplate.query(Query,
                (rs,rowNum) -> {
                    User t = new User(rs.getString("nickname"),rs.getString("userImg"),rs.getString("prologTitle"));
                    Pet p = new Pet(rs.getInt("idx"),rs.getString("name"),rs.getString("imgUrl"),rs.getString("type"),rs.getString("birth"));
                    petList.add(p);
                    return t;
                }, userIdx);


        return new Book(user.get(0).getNickname(),user.get(0).getImgUrl(),user.get(0).getTitle(),petList);
    }

}
