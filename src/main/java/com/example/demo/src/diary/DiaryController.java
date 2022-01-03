package com.example.demo.src.diary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.lang.Integer;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexDate;

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
    @GetMapping("/lists")
    public BaseResponse<List<DiaryListDto>> getDiaryLists() {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            List<DiaryListDto> result = diaryProvider.getDiaryList(userIdxByJwt);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/lists")
    public BaseResponse<String> insertDiaryLists(@RequestBody DiaryListDto diaryListDto) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if (diaryListDto.getContext() == null){
                return new BaseResponse<>(ADD_LISTS_TEXT_EMPTY);
            }
            diaryService.insertDiaryList(diaryListDto, userIdxByJwt);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/lists")
    public BaseResponse<String> updateDiaryList(@RequestBody UpdateDiaryListsDto diaryListDto) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            for (String d : diaryListDto.getLists()){
                if (d == null){
                    return new BaseResponse<>(ADD_LISTS_TEXT_EMPTY);
                }
            }
            diaryService.updateDiaryList(diaryListDto.getLists(), userIdxByJwt);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @DeleteMapping("/lists/{listIdx}")
    public BaseResponse<String> deleteDiaryList(@PathVariable("listIdx") Integer listIdx) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            diaryService.deleteDiaryList(listIdx, userIdxByJwt);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
