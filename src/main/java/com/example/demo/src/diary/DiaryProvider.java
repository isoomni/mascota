package com.example.demo.src.diary;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.diary.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class DiaryProvider {

    private final DiaryDao diaryDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DiaryProvider(DiaryDao diaryDao) {
        this.diaryDao = diaryDao;
    }

    public List<GetDiaryRes> getDiaryById(int listIdx) throws BaseException{
        try{
            List<GetDiaryRes> getDiarysRes = diaryDao.getDiarylist(listIdx);
            return getDiarysRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetDiaryById getDiaryDetailById(int diaryIdx) throws BaseException{
        try{
            GetDiaryDetail chk = diaryDao.chkDiary(diaryIdx);

            GetDiaryById getDiarysRes = diaryDao.getDiaryDetail(diaryIdx);
            return getDiarysRes;
        }
        catch (Exception exception) {
            if (exception instanceof EmptyResultDataAccessException){
                throw new BaseException(POST_DIARYS_NONE);
            }
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Lists> getDiarylists(int userIdx) throws BaseException{
        try{
            List<Lists> getDiarysRes = diaryDao.getDiarylists(userIdx);
            return getDiarysRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
