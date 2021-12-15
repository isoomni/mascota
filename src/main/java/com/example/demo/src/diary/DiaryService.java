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


}
