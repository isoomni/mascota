package com.example.demo.src.my;

import com.example.demo.config.BaseException;
import com.example.demo.src.my.model.PatchMyBookReq;
import com.example.demo.src.my.model.PatchMyPetReq;
import com.example.demo.src.my.model.PatchPetStatusReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class MyService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final MyDao myDao;
    @Autowired
    private final MyProvider myProvider;

    public MyService(MyDao myDao, MyProvider myProvider){
        this.myDao = myDao;
        this.myProvider = myProvider;
    }

    /**
     * 책 표지 수정
     * [PATCH] /myPages/books/:userIdx
     * @return BaseResponse<String>
     * */
    public void modifyMyBook(PatchMyBookReq patchMyBookReq) throws BaseException {
        try{
            int result = myDao.modifyMyBook(patchMyBookReq);
            if (result == 0){
                throw new BaseException(FAIL_MODIFY_BOOK);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * 동물 프로필 수정
     * [PATCH] /myPages/petInfo/:userIdx/:petIdx
     * @return BaseResponse<String>
     * */
    public void modifyMyPet(PatchMyPetReq patchMyPetReq) throws BaseException {
        try{
            int result = myDao.modifyMyPet(patchMyPetReq);
            if (result == 0){
                throw new BaseException(FAIL_MODIFY_PET_INFO);
            }
        } catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * 동물 프로필 삭제 API
     * [GET] /myPages/petInfo/:userIdx/:petIdx/status
     * @return BaseResponse<String>
     */
    public void deleteMyPet(PatchPetStatusReq patchPetStatusReq) throws BaseException {
        try{
            int result = myDao.deleteMyPet(patchPetStatusReq);
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
