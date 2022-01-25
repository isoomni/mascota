package com.example.demo.src.memory;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.memory.model.GetAnsweredMemoryRes;
import com.example.demo.src.memory.model.GetNotAnsweredMemoryRes;
import com.example.demo.src.memory.model.GetOneMemoryRes;
import com.example.demo.src.ready.model.GetOneReadyRes;
import com.example.demo.src.ready.model.GetReadyRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/memory")
public class MemoryController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MemoryProvider memoryProvider;
    @Autowired
    private final MemoryService memoryService;
    @Autowired
    private final JwtService jwtService;

    public MemoryController(MemoryProvider memoryProvider, MemoryService memoryService, JwtService jwtService){
        this.memoryProvider = memoryProvider;
        this.memoryService = memoryService;
        this.jwtService = jwtService;
    }

    /**
     * 추억하기 전체 질문 조회 (답변하기 탭)
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetNotAnsweredMemoryRes>>
     * */
    @ResponseBody
    @GetMapping("/{userIdx}/{petIdx}")
    public BaseResponse<List<GetNotAnsweredMemoryRes>> getNotAnsweredMemory(@PathVariable("userIdx") int userIdx, @PathVariable("petIdx") int petIdx){
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            // 같다면
            List<GetNotAnsweredMemoryRes> getNotAnsweredMemoryRes = memoryProvider.getNotAnsweredMemory(userIdx, petIdx);
            return new BaseResponse<>(getNotAnsweredMemoryRes);

        }  catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }






    /**
     * 추억하기 전체 질문 조회 (모아보기 탭) (질문인덱스순, 최신순, 오래된순)
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetAnsweredMemoryRes>>
     * */
    @ResponseBody
    @GetMapping("/{userIdx}/{petIdx}")
    public BaseResponse<List<GetAnsweredMemoryRes>> getAnsweredMemory(@PathVariable("userIdx") int userIdx, @PathVariable("petIdx") int petIdx, @RequestParam(required = false) String order){
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            if (order == null){ // 필터 적용이 하나도 없으면 다음과 같이
                List<GetAnsweredMemoryRes> getAnsweredMemoryRes = memoryProvider.getAnsweredMemory(petIdx, order);
                return new BaseResponse<>(getAnsweredMemoryRes);
            }
            if (order == "latest") {
                List<GetAnsweredMemoryRes> getAnsweredMemoryRes = memoryProvider.getAnsweredMemory(petIdx, order);
                return new BaseResponse<>(getAnsweredMemoryRes);
            }
            if (order == "oldest"){
                List<GetAnsweredMemoryRes> getAnsweredMemoryRes = memoryProvider.getAnsweredMemory(petIdx, order);
                return new BaseResponse<>(getAnsweredMemoryRes);
            }
            else {return new BaseResponse<>(UNVALID_FILTER);}// 필터 적용 불가


        }  catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }




    /**
     * 추억하기 개별 질문 조회
     * [GET] /ready/:userIdx/:readyAnswerIdx
     * @return BaseResponse<List<GetOneMemoryRes>>
     * */
    @ResponseBody
    @GetMapping("/{userIdx}/{readyAnswerIdx}")
    public BaseResponse<List<GetOneMemoryRes>> getOneMemory(@PathVariable("userIdx") int userIdx, @PathVariable("readyAnswerIdx") int readyAnswerIdx){
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            // 같다면
            List<GetOneMemoryRes> getOneMemoryRes = memoryProvider.getOneMemory(userIdx, readyAnswerIdx);
            return new BaseResponse<>(getOneMemoryRes);

        }  catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
