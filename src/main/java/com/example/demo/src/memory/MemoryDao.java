package com.example.demo.src.memory;

import com.example.demo.src.memory.model.*;
import com.example.demo.src.ready.model.PatchReadyAnswerReq;
import com.example.demo.src.ready.model.PostReadyAnswerReq;
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
        String Query = "SELECT mq.idx as questionNum, " +
                "REPLACE(mq.context, '*', (SELECT p.name FROM pet p WHERE p.idx = ?)) as question,\n" +
                "       (SELECT ma.context FROM memory_answer ma " +
                "WHERE ma.petIdx = ? AND mq.idx = ma.mqIdx) as answer\n" +
                "FROM memory_question mq\n" +
                "LEFT JOIN memory_answer ma on mq.idx = ma.mqIdx\n" +
                "where (SELECT ma.context FROM memory_answer ma " +
                "WHERE ma.petIdx = ? AND mq.idx = ma.mqIdx) is null;";

        int Params = petIdx;

        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new GetNotAnsweredMemoryRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("answer")
                ),Params, Params, Params);
    }



    /**
     * 추억하기 전체 질문 조회 (모아보기 탭) (질문인덱스순, 최신순, 오래된순)
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetAnsweredMemoryRes>>
     * */
    public List<GetAnsweredMemoryRes> getAnsweredMemory(int petIdx, String order){
        // 기본 - 질문 인덱스 순
        String Query1 = "SELECT mq.idx AS questionNum,\n" +
                "       REPLACE(mq.context, '*', p.name) as question,\n" +
                "       ma.context AS answer\n" +
                "        , date_format(ma.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM pet p\n" +
                "    LEFT JOIN memory_answer ma on p.idx = ma.petIdx\n" +
                "         LEFT JOIN memory_question mq on mq.idx = ma.mqIdx\n" +
                "WHERE ma.petIdx = ?\n" +
                "ORDER BY questionNum;";
        // 최신순
        String Query2 = "SELECT mq.idx AS questionNum,\n" +
                "       REPLACE(mq.context, '*', p.name) as question,\n" +
                "       ma.context AS answer\n" +
                "        , date_format(ma.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM pet p\n" +
                "         LEFT JOIN memory_answer ma on p.idx = ma.petIdx\n" +
                "         LEFT JOIN memory_question mq on mq.idx = ma.mqIdx\n" +
                "WHERE ma.petIdx = ?\n" +
                "ORDER BY ma.updatedAt DESC;";

        // 오래된 순
        String Query3 = "SELECT mq.idx AS questionNum,\n" +
                "       REPLACE(mq.context, '*', p.name) as question,\n" +
                "       ma.context AS answer\n" +
                "        , date_format(ma.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM pet p\n" +
                "         LEFT JOIN memory_answer ma on p.idx = ma.petIdx\n" +
                "         LEFT JOIN memory_question mq on mq.idx = ma.mqIdx\n" +
                "WHERE ma.petIdx = ?\n" +
                "ORDER BY ma.updatedAt ASC;";

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
        if (order.equals("latest")){
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
    public List<GetOneMemoryRes> getOneMemory(int userIdx, int memoryAnswerIdx){
        String Query = "SELECT mq.idx as questionNum, REPLACE(mq.context, '*', p.name) as question,\n" +
                "       ma.context AS answer\n" +
                "        , date_format(ma.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM pet p\n" +
                "         LEFT JOIN memory_answer ma on p.idx = ma.petIdx\n" +
                "         LEFT JOIN memory_question mq on mq.idx = ma.mqIdx\n" +
                "WHERE ma.idx = ? and ma.mqIdx = mq.idx and ma.context IS NOT NULL;";

        int Params = memoryAnswerIdx;

        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new GetOneMemoryRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("updatedAt")
                ),Params);
    }

    /**
     * 추억하기 개별 답변 작성 API
     * [PATCH] /memories/one/:userIdx/:petIdx/:memoryQuestionIdx
     * @return BaseResponse<String>
     */
    public int createMemoryAnswer(PostMemoryAnswerReq postMemoryAnswerReq){
        String createUserQuery = "insert into memory_answer (petIdx, mqIdx, context) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{postMemoryAnswerReq.getPetIdx(), postMemoryAnswerReq.getMemoryQuestionIdx(), postMemoryAnswerReq.getContext()};

        return this.jdbcTemplate.update(createUserQuery, createUserParams);
    }

    /**
     * 추억하기 개별 답변 수정 API
     * [PATCH] /memories/one/:userIdx/:memoryAnswerIdx
     * @return BaseResponse<String>
     */
    public int modifyMemoryAnswer(PatchMemoryAnswerReq patchMemoryAnswerReq){
        String modifyUserNameQuery = "update memory_answer set context where idx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchMemoryAnswerReq.getContext(), patchMemoryAnswerReq.getMemoryAnswerIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }



}
