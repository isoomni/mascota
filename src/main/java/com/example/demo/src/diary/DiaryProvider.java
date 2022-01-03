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

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;
import static com.example.demo.config.BaseResponseStatus.*;


//Provider : Read의 비즈니스 로직 처리
@Service
@Transactional
public class DiaryProvider {

    private final JwtService jwtService;

    @Autowired
    DiaryListRepository diaryListRepository;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DiaryProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public List<DiaryListDto> getDiaryList(Integer userIdx) throws BaseException {
        try{
            User user = new User(userIdx);
            List<DiaryList> result = diaryListRepository.findByUserOrderByNumAsc(user);
            List<DiaryListDto> list = new ArrayList<>();
            result.forEach(d -> {
                list.add(new DiaryListDto(d));
            });
            return list;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
