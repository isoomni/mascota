package com.example.demo.src.memory;


import com.example.demo.config.BaseException;
import com.example.demo.src.memory.model.PatchMemoryAnswerReq;
import com.example.demo.src.memory.model.PatchMemoryAnswerStatusReq;
import com.example.demo.src.memory.model.PostMemoryAnswerReq;
import com.example.demo.src.my.model.PatchPetStatusReq;
import com.example.demo.src.ready.model.PatchReadyAnswerReq;
import com.example.demo.src.ready.model.PostReadyAnswerReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class MemoryService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MemoryDao memoryDao;
    private final MemoryProvider memoryProvider;

    @Autowired
    public MemoryService(MemoryDao memoryDao, MemoryProvider memoryProvider){
        this.memoryDao = memoryDao;
        this.memoryProvider = memoryProvider;
    }

    /**
     * 추억하기 답변 작성 API
     * [POST] /memories/one/answer/:userIdx/:petIdx/:memoryQuestionIdx
     * @return BaseResponse<String>
     */
    public void createMemoryAnswer(PostMemoryAnswerReq postMemoryAnswerReq) throws BaseException {
        // 존재하면 이미 존재한다는 메세지 출력
        if(checkMemoryAnswerExist(postMemoryAnswerReq.getPetIdx(), postMemoryAnswerReq.getMemoryQuestionIdx()) == 1){ // true=1, 존재함
            throw new BaseException(EXIST_ANSWER);
        }
        try{

            int result = memoryDao.createMemoryAnswer(postMemoryAnswerReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**checkAnswerExist*/
    public int checkMemoryAnswerExist(int petIdx, int questionIdx) throws BaseException{
        try {
            return memoryDao.checkMemoryAnswerExist(petIdx, questionIdx);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 추억하기 답변 수정 API
     * [PATCH] /memories/one/answer/:userIdx/:memoryAnswerIdx
     * @return BaseResponse<String>
     */
    public void modifyMemoryAnswer(PatchMemoryAnswerReq patchMemoryAnswerReq) throws BaseException {
        if (checkMANotExist(patchMemoryAnswerReq.getMemoryAnswerIdx())==0){ // memoryAnswerIdx가 데이터베이스에 존재하지 않을 때 예외처리 필요
            throw new BaseException(IDX_NOT_EXIST);
        }
        if (checkMAAlreadyDelete(patchMemoryAnswerReq.getMemoryAnswerIdx()) == 0){ // status가 N일 때, 이미 삭제된 답변입니다.
            throw new BaseException(ALREADY_DELETE_ANSWER);
        }
        try{
            int result = memoryDao.modifyMemoryAnswer(patchMemoryAnswerReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 추억하기 답변 삭제 API
     * [PATCH] /memories/one/answer/:userIdx/:memoryAnswerIdx/status
     * @return BaseResponse<String>
     */
    public void deleteMemoryAnswer(PatchMemoryAnswerStatusReq patchMemoryAnswerStatusReq) throws BaseException {

        if (checkMANotExist(patchMemoryAnswerStatusReq.getMemoryAnswerIdx())==0){ // memoryAnswerIdx가 데이터베이스에 존재하지 않을 때 예외처리 필요
            throw new BaseException(IDX_NOT_EXIST);
        }
        if (checkMAAlreadyDelete(patchMemoryAnswerStatusReq.getMemoryAnswerIdx()) == 0){ // status가 N일 때, 이미 삭제된 답변입니다.
            throw new BaseException(ALREADY_DELETE_ANSWER);
        }
        try{
            int result = memoryDao.deleteMemoryAnswer(patchMemoryAnswerStatusReq);
            if (result == 0){
                throw new BaseException(FAIL_DELETE_PET);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**checkMANotExist
     * memoryAnswerIdx가 데이터베이스에 존재하지 않을 때 예외처리
     */
    public int checkMANotExist(int memoryAnswerIdx) throws BaseException{
        try{
            return memoryDao.checkMANotExist(memoryAnswerIdx);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**checkMAAlreadyDelete
     * status가 N일 때, 이미 삭제된 답변입니다.*/
    public int checkMAAlreadyDelete(int memoryAnswerIdx) throws BaseException{
        try{
            return memoryDao.checkMAAlreadyDelete(memoryAnswerIdx);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }



    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        logger.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
        logger.warn("Warn Level 테스트");
//        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
//        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
        logger.error("ERROR Level 테스트");

        return "Success Test";
    }
}
