package com.example.demo.src.diary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

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
    @GetMapping("/lists/{type}")
    public BaseResponse<List<DiaryListDto>> getDiaryLists(@PathVariable("type") int type) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if (type != 1 && type != 2){
                return new BaseResponse<>(NONE_TYPED);
            }

            List<DiaryListDto> result = diaryProvider.getDiaryList(userIdxByJwt, type);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/lists/{type}")
    public BaseResponse<String> insertDiaryLists(@PathVariable("type") int type, @RequestBody DiaryListDto diaryListDto) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if (diaryListDto.getContext() == null){
                return new BaseResponse<>(ADD_LISTS_TEXT_EMPTY);
            }

            if (type != 1 && type != 2){
                return new BaseResponse<>(NONE_TYPED);
            }

            diaryService.insertDiaryList(diaryListDto, userIdxByJwt, type);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/lists/{type}")
    public BaseResponse<String> updateDiaryList(@PathVariable("type") int type, @RequestBody UpdateDiaryListsDto diaryListDto) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            for (String d : diaryListDto.getLists()){
                if (d == null){
                    return new BaseResponse<>(ADD_LISTS_TEXT_EMPTY);
                }
            }

            if (type != 1 && type != 2){
                return new BaseResponse<>(NONE_TYPED);
            }

            diaryService.updateDiaryList(diaryListDto.getLists(), userIdxByJwt, type);
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

    @ResponseBody
    @PostMapping("/{listIdx}")
    public BaseResponse<String> insertDiary(@PathVariable("listIdx") Integer listIdx, @RequestBody DiaryDto diaryDto) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            diaryService.insertDiary(listIdx, diaryDto, userIdxByJwt);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{diaryIdx}")
    public BaseResponse<String> updateDiary(@PathVariable("diaryIdx") Integer diaryIdx, @RequestBody DiaryDto diaryDto) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            diaryService.updateDiary(diaryIdx, diaryDto, userIdxByJwt);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @DeleteMapping("/{diaryIdx}")
    public BaseResponse<String> deleteDiary(@PathVariable("diaryIdx") Integer diaryIdx) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            diaryService.deleteDiary(diaryIdx, userIdxByJwt);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{diaryIdx}")
    public BaseResponse<ResponseDiaryDto> getDiary(@PathVariable("diaryIdx") Integer diaryIdx) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            ResponseDiaryDto result = diaryProvider.getDiary(diaryIdx);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/home/{type}")
    public BaseResponse<ResponseDiaryHome> getDiaryHome(@PathVariable("type") Integer type, @RequestParam(required = false) Integer listIdx, @PageableDefault(size = 10) Pageable pageable) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if (type != 1 && type != 2){
                return new BaseResponse<>(NONE_TYPED);
            }

            ResponseDiaryHome result = diaryProvider.getDiaryHome(userIdxByJwt,type,listIdx, pageable);

            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/help")
    public BaseResponse<HelpHome> getDiaryHelp() {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            HelpHome result = diaryProvider.getDiaryHelp(userIdxByJwt);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/miss")
    public BaseResponse<Miss> getDiaryHelp(@RequestParam(required = true) String name) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if (name.length() == 0){
                return new BaseResponse<>(NONE_NAME_EXIST);
            }
            Miss result = diaryProvider.getMiss(userIdxByJwt,name);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    

}
