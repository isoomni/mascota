package com.example.demo.src.ready;

import com.example.demo.config.BaseException;
import com.example.demo.src.home.HomeDao;
import com.example.demo.src.home.model.GetHomeRes;
import com.example.demo.src.ready.model.GetOneReadyRes;
import com.example.demo.src.ready.model.GetReadyRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ReadyProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReadyDao readyDao;

    @Autowired
    public ReadyProvider(ReadyDao readyDao){
        this.readyDao = readyDao;
    }

    /**
     * 준비하기 전체 질문 조회
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetReadyRes>>
     * */
    public List<GetReadyRes> getReady(int userIdx, int petIdx) throws BaseException {
        try{
            List<GetReadyRes> getReadyRes = readyDao.getReady(userIdx, petIdx);
            return getReadyRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 준비하기 개별 질문 조회
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetOneReadyRes>>
     * */
    public List<GetOneReadyRes> getOneReady(int userIdx, int readyAnswerIdx) throws BaseException {
        try{
            List<GetOneReadyRes> getOneReadyRes = readyDao.getOneReady(userIdx, readyAnswerIdx);
            return getOneReadyRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
