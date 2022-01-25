package com.example.demo.src.ready;

import com.example.demo.src.home.model.GetHomeRes;
import com.example.demo.src.ready.model.GetOneReadyRes;
import com.example.demo.src.ready.model.GetReadyRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReadyDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 준비하기 전체 질문 조회
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetReadyRes>>
     * */
    public List<GetReadyRes> getReady(int userIdx, int petIdx){
        String getReadyQuery = "SELECT rq.idx as questionNum, " +
                "REPLACE(rq.context, '*', (SELECT p.name FROM pet p WHERE p.idx = ?)) as question,\n" +
                "       CASE WHEN ra.status = 'Y' AND ra.petIdx = ? THEN 'Y'\n" +
                "            ELSE 'N'\n" +
                "            END AS existenceOfAnswer\n" +
                "FROM ready_question rq\n" +
                "LEFT JOIN ready_answer ra on rq.idx = ra.rqIdx;";

        int getReadyParams2 = petIdx;

        return this.jdbcTemplate.query(getReadyQuery,
                (rs, rowNum) -> new GetReadyRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("existenceOfAnswer")
                ),getReadyParams2, getReadyParams2);
    }

    /**
     * 준비하기 개별 질문 조회
     * [GET] /ready/:userIdx/:readyAnswerIdx
     * @return BaseResponse<List<GetOneReadyRes>>
     * */
    public List<GetOneReadyRes> getOneReady(int userIdx, int readyAnswerIdx){
        String getOneReadyQuery = "SELECT rq.idx as questionNum, " +
                "REPLACE(rq.context, '*', p.name) as question, ra.context as answer\n" +
                "        ,date_format(ra.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM ready_question rq\n" +
                "         LEFT JOIN ready_answer ra on rq.idx = ra.rqIdx\n" +
                "LEFT JOIN pet p on ra.petIdx = p.idx\n" +
                "WHERE ra.idx = ?;";


        int getReadyParams2 = readyAnswerIdx;

        return this.jdbcTemplate.query(getOneReadyQuery,
                (rs, rowNum) -> new GetOneReadyRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("updatedAt")
                ),getReadyParams2);
    }

}
