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
    public List<GetAnsweredMemoryRes> getAnsweredMemory(int userIdx, int petIdx) throws BaseException {
        try{
            List<GetAnsweredMemoryRes> getAnsweredMemoryRes = memoryDao.getAnsweredMemory(userIdx, petIdx);
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
    public List<GetOneMemoryRes> getOneMemory(int userIdx, int readyAnswerIdx) throws BaseException {
        try{
            List<GetOneMemoryRes> getOneMemoryRes = memoryDao.getOneMemory(userIdx, readyAnswerIdx);
            return getOneMemoryRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
