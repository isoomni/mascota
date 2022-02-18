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
                "WHERE idx = 6;";

        // 책 정보
        String Query2 = "SELECT u.imgurl, u.title, u.nickname\n" +
                "FROM user as u\n" +
                "WHERE idx = 6;";

        // 반려동물 정보 list
        String Query3 = "SELECT imgurl, name, type, " +
                "DATE_FORMAT(birth,'%Y.%m.%d') as petBecomeFamilyDay\n" +
                "FROM pet as p\n" +
                "WHERE user_id = 6;";

        int Params = userIdx;


        return new GetMyPageRes(
                getMyUser = this.jdbcTemplate.queryForObject(Query1,
                        (rs, rowNum)-> new GetMyUser(
                                rs.getString("userNickname"),
                                rs.getString("userId")
                        ),Params),
                getMyBook = this.jdbcTemplate.queryForObject(Query2,
                        (rs, rowNum) -> new GetMyBook(
                                rs.getString("bookImgUrl"),
                                rs.getString("bookTitle"),
                                rs.getString("userNickname")
                        ),Params),
                getMyPetsList = this.jdbcTemplate.query(Query3,
                        (rs, rowNum) -> new GetMyPets(
                                rs.getString("petImgUrl"),
                                rs.getString("petName"),
                                rs.getString("petType"),
                                rs.getDate("petBecomeFamilyDay")
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
                "WHERE idx = 6;";

        int Params = userIdx;

        return this.jdbcTemplate.queryForObject(Query1,
                        (rs, rowNum)-> new GetMyInfoRes(
                                rs.getString("userNickname"),
                                rs.getString("userId")
                        ),Params);
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
