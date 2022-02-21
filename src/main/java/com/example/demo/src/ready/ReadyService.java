package com.example.demo.src.ready;

import com.example.demo.config.BaseException;
import com.example.demo.src.home.HomeDao;
import com.example.demo.src.home.HomeProvider;
import com.example.demo.src.memory.model.PatchMemoryAnswerStatusReq;
import com.example.demo.src.ready.model.PatchReadyAnswerReq;
import com.example.demo.src.ready.model.PatchReadyAnswerStatusReq;
import com.example.demo.src.ready.model.PostReadyAnswerReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ReadyService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReadyDao readyDao;
    private final ReadyProvider readyProvider;

    @Autowired
    public ReadyService(ReadyDao readyDao, ReadyProvider readyProvider){
        this.readyDao = readyDao;
        this.readyProvider = readyProvider;
    }

    /**
     * 준비하기 답변 작성 API
     * [PATCH] /readies/ones/:userIdx/:readyQuestionIdx
     * @return BaseResponse<String>
     */
    public void createReadyAnswer(PostReadyAnswerReq postReadyAnswerReq) throws BaseException {
        // 존재하면 이미 존재한다는 메세지 출력
        if(checkReadyAnswerExist(postReadyAnswerReq.getPetIdx(), postReadyAnswerReq.getReadyQuestionIdx()) == 1){ // true=1, 존재함
            throw new BaseException(EXIST_ANSWER);
        }
        try{
            int result = readyDao.createReadyAnswer(postReadyAnswerReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**checkAnswerExist*/
    public int checkReadyAnswerExist(int petIdx, int questionIdx) throws BaseException{
        try {
            return readyDao.checkReadyAnswerExist(petIdx, questionIdx);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * 준비하기 답변 수정 API
     * [PATCH] /readies/ones/:userIdx/:readyAnswerIdx
     * @return BaseResponse<String>
     */
    public void modifyReadyAnswer(PatchReadyAnswerReq patchReadyAnswerReq) throws BaseException {
        try{
            int result = readyDao.modifyReadyAnswer(patchReadyAnswerReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 준비하기 답변 삭제 API
     * [PATCH] /readies/one/answer/:userIdx/:readyAnswerIdx/status
     * @return BaseResponse<String>
     */
    public void deleteReadyAnswer(PatchReadyAnswerStatusReq patchReadyAnswerStatusReq) throws BaseException {
        try{
            int result = readyDao.deleteReadyAnswer(patchReadyAnswerStatusReq);
            if (result == 0){
                throw new BaseException(FAIL_DELETE_PET);
            }
        } catch(Exception exception){
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
