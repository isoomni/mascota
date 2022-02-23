package com.example.demo.src.my;

import com.example.demo.config.BaseException;
import com.example.demo.src.my.model.GetMyInfoRes;
import com.example.demo.src.my.model.GetMyPageRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MyProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MyDao myDao;


    public MyProvider(MyDao myDao){
        this.myDao = myDao;
    }

    /**
     * 마이페이지 전체 조회
     * [GET] /myPages/:userIdx
     * @return BaseResponse<List<GetMyRes>>
     * */
    public GetMyPageRes getMyPage(int userIdx) throws BaseException {
        try{
            GetMyPageRes getMyPageRes = myDao.getMyPage(userIdx);
            return getMyPageRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 마이페이지 개인정보 조회
     * [GET] /myPages/myInfo/:userIdx
     * @return BaseResponse<GetMyInfoRes>
     * */
    public GetMyInfoRes getMyInfo(int userIdx) throws BaseException {
        try{
            GetMyInfoRes getMyInfoRes = myDao.getMyInfo(userIdx);
            return getMyInfoRes;
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
