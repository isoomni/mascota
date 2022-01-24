package com.example.demo.src.memory;

import com.example.demo.src.memory.model.GetNotAnsweredMemoryRes;
import com.example.demo.src.memory.model.GetOneMemoryRes;
import com.example.demo.src.ready.model.GetReadyRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MemoryDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 추억하기 전체 질문 조회 (답변하기 탭)
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetNotAnsweredMemoryRes>>
     * */
    public List<GetNotAnsweredMemoryRes> getNotAnsweredMemory(int userIdx, int petIdx){
        String getNotAnsweredMemoryQuery = "";

        int Params = petIdx;

        return this.jdbcTemplate.query(getNotAnsweredMemoryQuery,
                (rs, rowNum) -> new GetNotAnsweredMemoryRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("existenceOfAnswer")
                ),Params);
    }



    /**
     * 추억하기 전체 질문 조회 (모아보기 탭) (질문인덱스순, 최신순, 오래된순)
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetAnsweredMemoryRes>>
     * */




    /**
     * 추억하기 개별 질문 조회
     * [GET] /ready/:userIdx/:readyAnswerIdx
     * @return BaseResponse<List<GetOneMemoryRes>>
     * */
}
