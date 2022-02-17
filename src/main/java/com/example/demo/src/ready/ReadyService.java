package com.example.demo.src.ready;

import com.example.demo.config.BaseException;
import com.example.demo.src.home.HomeDao;
import com.example.demo.src.home.HomeProvider;
import com.example.demo.src.ready.model.PatchReadyAnswerReq;
import com.example.demo.src.ready.model.PostReadyAnswerReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL;

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
     * 준비하기 개별 답변 작성 API
     * [PATCH] /readies/ones/:userIdx/:readyQuestionIdx
     * @return BaseResponse<String>
     */
    public void createReadyAnswer(PostReadyAnswerReq postReadyAnswerReq) throws BaseException {
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

    /**
     * 준비하기 개별 답변 수정 API
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


}
