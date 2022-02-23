package com.example.demo.src.ready;

import com.example.demo.src.home.model.GetHomeRes;
import com.example.demo.src.memory.model.PatchMemoryAnswerStatusReq;
import com.example.demo.src.ready.model.*;
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
     * 준비하기 답변 조회
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
                "WHERE ra.idx = ? AND ra.status = 'Y';";


        int getReadyParams2 = readyAnswerIdx;

        return this.jdbcTemplate.query(getOneReadyQuery,
                (rs, rowNum) -> new GetOneReadyRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("updatedAt")
                ),getReadyParams2);
    }

    /**
     * 준비하기 답변 작성 API
     * [PATCH] /readies/ones/:userIdx/:readyQuestionIdx
     * @return BaseResponse<String>
     */
    public int createReadyAnswer(PostReadyAnswerReq postReadyAnswerReq){
        String createUserQuery = "insert into ready_answer (petIdx, rqIdx, context) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{postReadyAnswerReq.getPetIdx(), postReadyAnswerReq.getReadyQuestionIdx(), postReadyAnswerReq.getContext()};

        return this.jdbcTemplate.update(createUserQuery, createUserParams);
    }

    /**checkAnswerExist*/
    public int checkReadyAnswerExist(int petIdx, int questionIdx){
        String Query = "select exists(select ra.rqIdx from ready_answer ra where petIdx = ? and rqIdx = ?)";
        int Params1 = petIdx;
        int Params2 = questionIdx;
        return this.jdbcTemplate.queryForObject(Query,
                int.class,
                Params1, Params2);
    }


    /**
     * 준비하기 답변 수정 API
     * [PATCH] /readies/ones/:userIdx/:readyAnswerIdx
     * @return BaseResponse<String>
     */
    public int modifyReadyAnswer(PatchReadyAnswerReq patchReadyAnswerReq){
        String Query = "update ready_answer ra set ra.context = ? where ra.idx = ? and ra.status = 'Y'";
        Object[] Params = new Object[]{patchReadyAnswerReq.getContext(), patchReadyAnswerReq.getReadyAnswerIdx()};

        return this.jdbcTemplate.update(Query,Params);
    }


    /**
     * 준비하기 답변 삭제 API
     * [PATCH] /readies/one/answer/:userIdx/:readyAnswerIdx/status
     * @return BaseResponse<String>
     */
    public int deleteReadyAnswer(PatchReadyAnswerStatusReq patchReadyAnswerStatusReq){
        String modifyOrderQuery = "UPDATE ready_answer ra set status = ? where idx = ? and ra.status = 'Y';";
        Object[] modifyOrderParams = new Object[]{patchReadyAnswerStatusReq.getStatus(), patchReadyAnswerStatusReq.getReadyAnswerIdx()};

        return this.jdbcTemplate.update(modifyOrderQuery,modifyOrderParams);
    }
    /**checkRANotExist
     * readyAnswerIdx가 데이터베이스에 존재하지 않을 때 예외처리
     */
    public int checkRANotExist(int readyAnswerIdx){
        String checkQuery = "select exists(select idx from ready_answer where idx = ?)";
        int checkParams = readyAnswerIdx;
        return this.jdbcTemplate.queryForObject(checkQuery,
                int.class,
                checkParams);

    }
    /**checkRAAlreadyDelete
     * status가 N일 때, 이미 삭제된 답변입니다.*/
    public int checkRAAlreadyDelete(int readyAnswerIdx){
        String checkQuery = "select (case status when 'N' THEN 0\n" +
                "                    when 'Y' then 1\n" +
                "    end) as 'checkStatus'\n" +
                "    from ready_answer where idx = ?;";
        int checkParams = readyAnswerIdx;
        return this.jdbcTemplate.queryForObject(checkQuery,
                int.class,
                checkParams);
    }

}
