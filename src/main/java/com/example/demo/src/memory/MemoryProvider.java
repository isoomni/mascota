package com.example.demo.src.memory;

import com.example.demo.config.BaseException;
import com.example.demo.src.memory.model.GetAnsweredMemoryRes;
import com.example.demo.src.memory.model.GetNotAnsweredMemoryRes;
import com.example.demo.src.memory.model.GetOneMemoryRes;
import com.example.demo.src.ready.ReadyDao;
import com.example.demo.src.ready.model.GetOneReadyRes;
import com.example.demo.src.ready.model.GetReadyRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MemoryProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MemoryDao memoryDao;

    @Autowired
    public MemoryProvider(MemoryDao memoryDao) {this.memoryDao = memoryDao;}

    /**
     * 추억하기 전체 질문 조회 (답변하기 탭)
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetNotAnsweredMemoryRes>>
     * */
    public List<GetNotAnsweredMemoryRes> getNotAnsweredMemory(int userIdx, int petIdx) throws BaseException {
        try{
            List<GetNotAnsweredMemoryRes> getNotAnsweredMemoryRes = memoryDao.getNotAnsweredMemory(userIdx, petIdx);
            return getNotAnsweredMemoryRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }



    /**
     * 추억하기 전체 질문 조회 (모아보기 탭) (질문인덱스순, 최신순, 오래된순)
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetAnsweredMemoryRes>>
     * */
    // 질문인덱스순
    public List<GetAnsweredMemoryRes> getAnsweredMemory(int petIdx, String order) throws BaseException {
        try{
            List<GetAnsweredMemoryRes> getAnsweredMemoryRes = memoryDao.getAnsweredMemory(petIdx, order);
            return getAnsweredMemoryRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }



    /**
     * 추억하기 개별 질문 조회
     * [GET] /ready/:userIdx/:readyAnswerIdx
     * @return BaseResponse<List<GetOneMemoryRes>>
     * */
    public List<GetOneMemoryRes> getOneMemory(int userIdx, int memoryAnswerIdx) throws BaseException {
        try{
            List<GetOneMemoryRes> getOneMemoryRes = memoryDao.getOneMemory(userIdx, memoryAnswerIdx);
            return getOneMemoryRes;
        } catch (Exception exception) {
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
