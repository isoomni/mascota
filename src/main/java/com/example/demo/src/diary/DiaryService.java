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

import javax.sql.DataSource;

import static com.example.demo.config.BaseResponseStatus.*;
import java.util.*;

// Service Create, Update, Delete 의 로직 처리
@Service
@Transactional
public class DiaryService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DiaryDao diaryDao;
    private final DiaryProvider diaryProvider;

    @Autowired
    public DiaryService(DiaryDao diaryDao, DiaryProvider diaryProvider) {
        this.diaryDao = diaryDao;
        this.diaryProvider = diaryProvider;

    }

    public GetDiaryDetail createDiary(PostDiaryReq postDiaryReq) throws BaseException {
        try{
            return diaryDao.postDiary(postDiaryReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateDiary(GetDiaryById getDiaryById) throws BaseException {
        try{
            GetDiaryDetail now = getDiaryById.getGetDiaryDetail();
            GetDiaryDetail chk = diaryDao.chkDiary(now.getIdx());
            if (chk == null){
                throw new BaseException(POST_DIARYS_NONE);
            }
            if (now.getTitle() == null){ now.setTitle(chk.getTitle()); }
            if (now.getContext() == null){ now.setContext(chk.getContext()); }
            if (now.getType() == null){ now.setType(chk.getType()); }
            if (now.getDate() == null){ now.setDate(chk.getDate()); }

            getDiaryById.setGetDiaryDetail(now);

            int result = diaryDao.updateDiary(getDiaryById);
            if (result == 0){
                throw new BaseException(FAIL_DIARYS_MODIFY);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteDiary(int diaryIdx) throws BaseException {
        try{
            GetDiaryDetail chk = diaryDao.chkDiary(diaryIdx);
            if (chk == null){
                throw new BaseException(POST_DIARYS_NONE);
            }

            int result = diaryDao.deleteDiary(diaryIdx);
            if (result == 0){
                throw new BaseException(FAIL_DIARYS_MODIFY);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void insertLists(int userIdx, String context) throws BaseException {
        try{
            List<Lists> chk = diaryDao.getlists(userIdx);
            int result = diaryDao.insertLists(userIdx, context, chk);
            if (result == 0){
                throw new BaseException(FAIL_LISTS_ADD);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateLists(int userIdx, List<String> lists) throws BaseException {
        try{
            List<Lists> chk = diaryDao.getlists(userIdx); // 원형
            int result = diaryDao.updateLists(userIdx, lists, chk);
            if (result == 0){
                throw new BaseException(FAIL_LISTS_ADD);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
