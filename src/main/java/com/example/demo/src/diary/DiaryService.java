package com.example.demo.src.diary;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import javax.sql.DataSource;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
@Transactional
public class DiaryService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DiaryProvider diaryProvider;
    private final JwtService jwtService;

    @Autowired
    DiaryListRepository diaryListRepository;

    @Autowired
    public DiaryService(DiaryProvider diaryProvider, JwtService jwtService) {
        this.diaryProvider = diaryProvider;
        this.jwtService = jwtService;
    }

    public void insertDiaryList(DiaryListDto diaryListDto, Integer userIdx) throws BaseException {
        try{
            User user = new User(userIdx);
            List<DiaryList> result = diaryListRepository.findByUserOrderByNumAsc(user);
            for (DiaryList d : result){
                if (d.getContext().equals(diaryListDto.getContext())){
                    throw new BaseException(FAIL_LISTS_ADD);
                }
            }
            Integer number = result.size() + 1;
            DiaryList insert = new DiaryList(user, diaryListDto, number);
            diaryListRepository.save(insert);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateDiaryList(List<String> diaryListDto, Integer userIdx) throws BaseException {
        try{
            User user = new User(userIdx);
            List<DiaryList> result = diaryListRepository.findByUserOrderByNumAsc(user);
            System.out.println(result.size() +  " " + diaryListDto.size());
            if (result.size() != diaryListDto.size()){
                throw new BaseException(FAIL_LISTS_ADD);
            }
            for (int i = 0; i < result.size(); i++){
                DiaryList update = result.get(i);
                update.setContext(diaryListDto.get(i));
                update.setNum(i+1);
                diaryListRepository.save(update);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteDiaryList(Integer listIdx, Integer userIdx) throws BaseException {
        try{
            Optional<DiaryList> result = diaryListRepository.findById(listIdx);
            if (result.isPresent()) {
                diaryListRepository.deleteById(listIdx);
            }
            else {
                throw new BaseException(FAIL_LISTS_DEL);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
