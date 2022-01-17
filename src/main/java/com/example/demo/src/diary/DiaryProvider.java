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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;
import java.util.Random;
import static com.example.demo.config.BaseResponseStatus.*;


//Provider : Read의 비즈니스 로직 처리
@Service
@Transactional
public class DiaryProvider {

    private final JwtService jwtService;

    @Autowired
    DiaryListRepository diaryListRepository;

    @Autowired
    DiaryImgRepository diaryImgRepository;

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    MoodRepository moodRepository;

    @Autowired
    HelpRepository helpRepository;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DiaryProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public List<DiaryListDto> getDiaryList(Integer userIdx, Integer type) throws BaseException {
        try{
            User user = new User(userIdx);
            List<DiaryList> result = diaryListRepository.findByUserAndTypeOrderByNumAsc(user,type);
            List<DiaryListDto> list = new ArrayList<>();
            result.forEach(d -> {
                list.add(new DiaryListDto(d));
            });
            return list;
        } catch (Exception exception) {
            if (exception instanceof BaseException){
                throw (BaseException)exception;
            }
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ResponseDiaryDto getDiary(Integer diaryIdx) throws BaseException {
        try{
            Optional<Diary> result = diaryRepository.findById(diaryIdx);

            if (result.isPresent()){
                return new ResponseDiaryDto(result.get());
            }
            else{
                throw new BaseException(DATABASE_ERROR);
            }
        } catch (Exception exception) {
            if (exception instanceof BaseException){
                throw (BaseException)exception;
            }
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ResponseDiaryHome getDiaryHome(Integer userIdx, Integer type, Integer listIdx, Pageable pageable) throws BaseException {
        try{
            User user = new User(userIdx);
            Optional<DiaryList> result;
            if (listIdx != null){
                result = diaryListRepository.findById(listIdx);
            }
            else{
                result = diaryListRepository.findTopByUserAndTypeOrderByNumAsc(user,type);
            }
            if (result.isPresent()){
                DiaryList list = result.get();
                int limit = diaryRepository.countByDiaryList(list);
                if (limit <= pageable.getPageSize() * pageable.getPageNumber()){
                    throw new BaseException(NONE_PAGE);
                }
                List<DiarySummary> records = diaryRepository.findByDiaryList(list,pageable);
                return new ResponseDiaryHome(list.getContext(),records);
            }
            else{
                throw new BaseException(NONE_PAGE);
            }
        } catch (Exception exception) {
            if (exception instanceof BaseException){
                throw (BaseException)exception;
            }
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public HelpHome getDiaryHelp(Integer userIdx) throws BaseException {
        try{
            User user = new User(userIdx);
            int limit = diaryRepository.countByUser(user);
            Random num = new Random();
            int idx = num.nextInt(limit);
            PageRequest pageRequest = PageRequest.of(idx, 1);
            List<DiarySummary> result = diaryRepository.findByUser(user,pageRequest);
            PageRequest request = PageRequest.of(0, 5);
            Page<Help> helpList = helpRepository.findAll(request);

            return new HelpHome(result.get(0),helpList.getContent());
        } catch (Exception exception) {
            if (exception instanceof BaseException){
                throw (BaseException)exception;
            }
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
