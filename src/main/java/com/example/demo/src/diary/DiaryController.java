package com.example.demo.src.diary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.diary.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/diarys")
public class DiaryController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final DiaryProvider diaryProvider;
    @Autowired
    private final DiaryService diaryService;
    @Autowired
    private final JwtService jwtService;

    public DiaryController(DiaryProvider diaryProvider, DiaryService diaryService, JwtService jwtService){
        this.diaryProvider = diaryProvider;
        this.diaryService = diaryService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetDiaryRes>> getUsers(@RequestParam(required = false) Integer listIdx) {
        try{
            if (listIdx == null){
                return new BaseResponse<>(LISTS_EMPTY_USER_ID);
            }
            List<GetDiaryRes> getDiarysRes = diaryProvider.getDiaryById(listIdx);
            return new BaseResponse<>(getDiarysRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{diaryIdx}")
    public BaseResponse<GetDiaryById> getUsers(@PathVariable int diaryIdx) {
        try{
            GetDiaryById getDiarysRes = diaryProvider.getDiaryDetailById(diaryIdx);
            return new BaseResponse<>(getDiarysRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/lists/{userIdx}")
    public BaseResponse<List<Lists>> getLists(@PathVariable int userIdx) {
        System.out.println(userIdx);
        try{
            List<Lists> Result = diaryProvider.getDiarylists(userIdx);
            return new BaseResponse<>(Result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<GetDiaryDetail> createUser(@RequestBody PostDiaryReq postDiaryReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postDiaryReq.getTitle() == null){
            return new BaseResponse<>(POST_DIARYS_EXISTS_TITLE);
        }

        if(postDiaryReq.getContext() == null){
            return new BaseResponse<>(POST_DIARYS_EXISTS_CONTEXT);
        }

        if(postDiaryReq.getDate() == null){
            return new BaseResponse<>(POST_DIARYS_EXISTS_DATE);
        }

        if(postDiaryReq.getImgUrls() == null || postDiaryReq.getImgUrls().isEmpty()){
            return new BaseResponse<>(POST_DIARYS_EXISTS_IMG);
        }

        if(postDiaryReq.getMoods() == null || postDiaryReq.getMoods().isEmpty()){
            return new BaseResponse<>(POST_DIARYS_EXISTS_MOOD);
        }

        for (Mood mood : postDiaryReq.getMoods()){
            if (mood.getPetIdx() == 0 || mood.getPetType() == null){
                return new BaseResponse<>(POST_DIARYS_EXISTS_ELEMENT);
            }
        }

        try{
            GetDiaryDetail last_insert = diaryService.createDiary(postDiaryReq);
            return new BaseResponse<>(last_insert);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
