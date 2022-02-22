package com.example.demo.src.my;

import com.example.demo.src.memory.model.GetAnsweredMemoryRes;
import com.example.demo.src.my.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MyDao {

    private JdbcTemplate jdbcTemplate;
    private GetMyUser getMyUser;
    private GetMyBook getMyBook;
    private List<GetMyPets> getMyPetsList;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 마이페이지 전체 조회
     * [GET] /myPages/:userIdx
     * @return BaseResponse<List<GetMyRes>>
     * */
    public GetMyPageRes getMyPage(int userIdx){
        // 유저 정보
        String Query1 = "SELECT u.nickname, u.id\n" +
                "FROM user as u\n" +
                "WHERE idx = ?;";

        // 책 정보
        String Query2 = "SELECT u.imgurl, u.title, u.nickname\n" +
                "FROM user as u\n" +
                "WHERE idx = ?;";

        // 반려동물 정보 list
        String Query3 = "SELECT imgurl, name, type, " +
                "DATE_FORMAT(birth,'%Y.%m.%d') as petBecomeFamilyDay\n" +
                "FROM pet as p\n" +
                "WHERE user_id = ? AND status = 'Y';";

        int Params = userIdx;


        return new GetMyPageRes(
                getMyUser = this.jdbcTemplate.queryForObject(Query1,
                        (rs, rowNum)-> new GetMyUser(
                                rs.getString("nickname"),
                                rs.getString("id")
                        ),Params),
                getMyBook = this.jdbcTemplate.queryForObject(Query2,
                        (rs, rowNum) -> new GetMyBook(
                                rs.getString("imgurl"),
                                rs.getString("title"),
                                rs.getString("nickname")
                        ),Params),
                getMyPetsList = this.jdbcTemplate.query(Query3,
                        (rs, rowNum) -> new GetMyPets(
                                rs.getString("imgurl"),
                                rs.getString("name"),
                                rs.getString("type"),
                                rs.getString("petBecomeFamilyDay")
                        ), Params)
        );

    }


    /**
     * 마이페이지 개인정보 조회
     * [GET] /myPages/myInfo/:userIdx
     * @return BaseResponse<GetMyInfoRes>
     * */
    public GetMyInfoRes getMyInfo(int userIdx){
        // 유저 정보
        String Query1 = "SELECT u.nickname, u.id\n" +
                "FROM user as u\n" +
                "WHERE idx = ?;";

        int Params = userIdx;

        return this.jdbcTemplate.queryForObject(Query1,
                        (rs, rowNum)-> new GetMyInfoRes(
                                rs.getString("nickname"),
                                rs.getString("id")
                        ),Params);
    }

    /**
     * 마이페이지 비밀번호 가져오기 FOR CHECK
     * */
    public UserPassword getPwd (PatchMyPasswordReq patchMyPasswordReq){
        String Query = "select u.idx, u.password from user u where idx = ?";
        int Params = patchMyPasswordReq.getUserIdx();

        return this.jdbcTemplate.queryForObject(Query,
                (rs,rowNum)-> new UserPassword(
                        rs.getInt("idx"),
                        rs.getString("password")
                ),
                Params
        );
    }
    /**
     * 마이페이지 비밀번호 변경
     * [PATCH] /myPages/myInfo/password/:userIdx
     * @return BaseResponse<String>
     * */
    public int modifyPassword(PatchMyPasswordReq patchMyPasswordReq){
        String createUserQuery = "update user set password = ? where idx = ?;";
        Object[] createUserParams = new Object[]{patchMyPasswordReq.getNewPassword(), patchMyPasswordReq.getUserIdx()};

        return this.jdbcTemplate.update(createUserQuery, createUserParams);
    }



    /**
     * 책 표지 수정
     * [PATCH] /myPages/books/:userIdx
     * @return BaseResponse<String>
     * */
    public int modifyMyBook(PatchMyBookReq patchMyBookReq){
        String modifyOrderQuery = "UPDATE user set imgurl = ?, title = ? , nickname = ? where idx = ?;";
        Object[] modifyOrderParams = new Object[]{patchMyBookReq.getBookImgUrl(), patchMyBookReq.getBookTitle(), patchMyBookReq.getUserNickname(), patchMyBookReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyOrderQuery,modifyOrderParams);
    }

    /**
     * 동물 프로필 수정
     * [PATCH] /myPages/petInfo/:userIdx/:petIdx
     * @return BaseResponse<String>
     * */
    public int modifyMyPet(PatchMyPetReq patchMyPetReq){
        String modifyOrderQuery = "UPDATE pet set imgurl = ?, name = ?, type = ?, birth = ? where idx = ?;";
        Object[] modifyOrderParams = new Object[]{patchMyPetReq.getPetImgUrl(), patchMyPetReq.getPetName(), patchMyPetReq.getPetType(), patchMyPetReq.getPetBecomeFamilyDay(), patchMyPetReq.getPetIdx()};

        return this.jdbcTemplate.update(modifyOrderQuery,modifyOrderParams);
    }


    /**
     * 동물 프로필 삭제 API
     * [GET] /myPages/petInfo/:userIdx/:petIdx/status
     * @return BaseResponse<String>
     */
    public int deleteMyPet(PatchPetStatusReq patchPetStatusReq){
        String modifyOrderQuery = "UPDATE pet set status = ? where idx = ?;";
        Object[] modifyOrderParams = new Object[]{patchPetStatusReq.getPetStatus(), patchPetStatusReq.getPetIdx()};

        return this.jdbcTemplate.update(modifyOrderQuery,modifyOrderParams);
    }


}
