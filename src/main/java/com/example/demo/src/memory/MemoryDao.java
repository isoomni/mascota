package com.example.demo.src.memory;

import com.example.demo.src.memory.model.GetAnsweredMemoryRes;
import com.example.demo.src.memory.model.GetNotAnsweredMemoryRes;
import com.example.demo.src.memory.model.GetOneMemoryRes;
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
        String Query = "SELECT mq.idx AS questionNum, " +
                "REPLACE(mq.context, '*', " +
                "(SELECT p.name FROM pet p WHERE p.idx = ?)) as question," +
                " ma.context AS answer\n" +
                "FROM memory_question mq\n" +
                "         LEFT JOIN memory_answer ma on mq.idx = ma.mqIdx\n" +
                "WHERE ISNULL(ma.context)\n" +
                ";";

        int Params = petIdx;

        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new GetNotAnsweredMemoryRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("answer")
                ),Params);
    }



    /**
     * 추억하기 전체 질문 조회 (모아보기 탭) (질문인덱스순, 최신순, 오래된순)
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetAnsweredMemoryRes>>
     * */
    public List<GetAnsweredMemoryRes> getAnsweredMemory(int petIdx, String order){
        // 기본 - 질문 인덱스 순
        String Query1 = "SELECT mq.idx AS questionNum,\n" +
                "       REPLACE(mq.context, '*', (SELECT p.name FROM pet p WHERE p.idx = ?)) as question,\n" +
                "       ma.context AS answer, date_format(ma.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM memory_question mq\n" +
                "         LEFT JOIN memory_answer ma on mq.idx = ma.mqIdx\n" +
                "WHERE ma.context IS NOT NULL\n" +
                "ORDER BY questionNum\n" +
                ";";
        // 최신순
        String Query2 = "SELECT mq.idx AS questionNum, REPLACE(mq.context, '*', (SELECT p.name FROM pet p WHERE p.idx = ?)) as question,\n" +
                "       ma.context AS answer, ma.updatedAt\n" +
                "FROM memory_question mq\n" +
                "         LEFT JOIN memory_answer ma on mq.idx = ma.mqIdx\n" +
                "WHERE ma.context IS NOT NULL\n" +
                "ORDER BY ma.updatedAt DESC\n" +
                ";";

        // 오래된 순
        String Query3 = "SELECT mq.idx AS questionNum, REPLACE(mq.context, '*', (SELECT p.name FROM pet p WHERE p.idx = ?)) as question,\n" +
                "       ma.context AS answer, ma.updatedAt\n" +
                "FROM memory_question mq\n" +
                "         LEFT JOIN memory_answer ma on mq.idx = ma.mqIdx\n" +
                "WHERE ma.context IS NOT NULL\n" +
                "ORDER BY ma.updatedAt ASC\n" +
                ";";

        int Params = petIdx;

        if (order == null){
            return this.jdbcTemplate.query(Query2,
                    (rs, rowNum) -> new GetAnsweredMemoryRes(
                            rs.getInt("questionNum"),
                            rs.getString("question"),
                            rs.getString("answer"),
                            rs.getString("updatedAt")
                    ),Params);
        }
        if (order == "latest"){
            return this.jdbcTemplate.query(Query3,
                    (rs, rowNum) -> new GetAnsweredMemoryRes(
                            rs.getInt("questionNum"),
                            rs.getString("question"),
                            rs.getString("answer"),
                            rs.getString("updatedAt")
                    ),Params);
        }
        return this.jdbcTemplate.query(Query1,
                (rs, rowNum) -> new GetAnsweredMemoryRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("updatedAt")
                ),Params);


    }



    /**
     * 추억하기 개별 질문 조회
     * [GET] /ready/:userIdx/:readyAnswerIdx
     * @return BaseResponse<List<GetOneMemoryRes>>
     * */
    public List<GetOneMemoryRes> getOneMemory(int userIdx, int petIdx){
        String Query = "SELECT mq.idx as questionNum, REPLACE(mq.context, '*', p.name) as question, " +
                "ma.context as answer\n" +
                "    ,date_format(ma.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM memory_question mq\n" +
                "         LEFT JOIN memory_answer ma on mq.idx = ma.mqIdx\n" +
                "         LEFT JOIN pet p on ma.petIdx = p.idx\n" +
                "WHERE ma.idx = 2;";

        int Params = petIdx;

        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new GetOneMemoryRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("updatedAt")
                ),Params);
    }
}
