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
    public BaseResponse<GetDiaryDetail> createDiary(@RequestBody PostDiaryReq postDiaryReq) {
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

        if (postDiaryReq.getImgUrls().size() > 3){
            return new BaseResponse<>(POST_DIARYS_EXCEED_IMG);
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

    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> updateDiary(@RequestBody GetDiaryById getDiaryById) {
        System.out.println(getDiaryById.getGetDiaryDetail());
        if (getDiaryById.getImgUrls().size() > 3){
            return new BaseResponse<>(POST_DIARYS_EXCEED_IMG);
        }

        for (Mood mood : getDiaryById.getMoods()){
            if (mood.getPetIdx() == 0 || mood.getPetType() == null){
                return new BaseResponse<>(POST_DIARYS_EXISTS_ELEMENT);
            }
        }

        try{
            diaryService.updateDiary(getDiaryById);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{diaryIdx}")
    public BaseResponse<String> deleteDiary(@PathVariable int diaryIdx) {
        try{
            diaryService.deleteDiary(diaryIdx);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/lists")
    public BaseResponse<String> insertLists(@RequestBody ListReq lists) {
        try{
            if (lists.getContext() == null){
                return new BaseResponse<>(ADD_LISTS_TEXT_EMPTY);
            }
            diaryService.insertLists(lists.getUserIdx(),lists.getContext());
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/lists")
    public BaseResponse<String> updateLists(@RequestBody ListReq lists) {
        try{
            diaryService.updateLists(lists.getUserIdx(),lists.getLists());
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
