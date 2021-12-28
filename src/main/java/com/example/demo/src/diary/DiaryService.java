package com.example.demo.src.diary;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.diary.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;

import static com.example.demo.config.BaseResponseStatus.*;
import java.util.*;

// Service Create, Update, Delete 의 로직 처리
@Service

public class DiaryService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DiaryDao diaryDao;
    private final DiaryProvider diaryProvider;

    @Autowired
    public DiaryService(DiaryDao diaryDao, DiaryProvider diaryProvider) {
        this.diaryDao = diaryDao;
        this.diaryProvider = diaryProvider;

    }

    @Transactional(rollbackFor = BaseException.class)
    public int createDiary(PostDiaryReq postDiaryReq) throws BaseException {
        try{
            return diaryDao.postDiary(postDiaryReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = BaseException.class)
    public void updateDiary(int idx, GetDiaryById getDiaryById) throws BaseException {
        try{
            GetDiaryDetail now = getDiaryById.getGetDiaryDetail();
            GetDiaryDetail chk = diaryDao.chkDiary(now.getIdx());

            if (now.getTitle() == null){ now.setTitle(chk.getTitle()); }
            if (now.getContext() == null){ now.setContext(chk.getContext()); }
            if (now.getType() == null){ now.setType(chk.getType()); }
            if (now.getDate() == null){ now.setDate(chk.getDate()); }
            now.setUserIdx(idx);
            getDiaryById.setGetDiaryDetail(now);

            int result = diaryDao.updateDiary(getDiaryById);
            if (result == 0){
                throw new BaseException(DATABASE_ERROR);
            }

        } catch (Exception exception) {
            if (exception instanceof EmptyResultDataAccessException){
                throw new BaseException(POST_DIARYS_NONE);
            }
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = BaseException.class)
    public void deleteDiary(int idx, int diaryIdx) throws BaseException {
        try{
            GetDiaryDetail chk = diaryDao.chkDiary(diaryIdx);
            int result = diaryDao.deleteDiary(diaryIdx);
            if (result == 0){
                throw new BaseException(DATABASE_ERROR);
            }

        } catch (Exception exception) {
            if (exception instanceof EmptyResultDataAccessException){
                throw new BaseException(NONE_DIARY_EXIST);
            }
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = BaseException.class)
    public void insertLists(int userIdx, String context) throws BaseException {
        try{
            List<Lists> chk = diaryDao.getlists(userIdx);
            int result = diaryDao.insertLists(userIdx, context, chk);
            if (result == 0){
                throw new BaseException(DATABASE_ERROR);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = BaseException.class)
    public void updateLists(int userIdx, List<String> lists) throws BaseException {
        try{
            List<Lists> chk = diaryDao.getlists(userIdx); // 원형
            int result = diaryDao.updateLists(userIdx, lists, chk);
            if (result == 0){
                throw new BaseException(DATABASE_ERROR);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = BaseException.class)
    public void deleteLists(int listIdx) throws BaseException {
        try{
            int result = diaryDao.deleteLists(listIdx);
            if (result == 0){
                throw new BaseException(DATABASE_ERROR);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
