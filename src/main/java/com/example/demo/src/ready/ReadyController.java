package com.example.demo.src.ready;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.ready.model.GetOneReadyRes;
import com.example.demo.src.ready.model.GetReadyRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/ready")
public class ReadyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReadyProvider readyProvider;
    @Autowired
    private final ReadyService readyService;
    @Autowired
    private final JwtService jwtService;

    public ReadyController(ReadyProvider readyProvider, ReadyService readyService, JwtService jwtService){
        this.readyProvider = readyProvider;
        this.readyService = readyService;
        this.jwtService = jwtService;
    }
    /**
     * 준비하기 전체 질문 조회
     * [GET] /ready/:userIdx/:petIdx
     * @return BaseResponse<List<GetReadyRes>>
     * */
    @ResponseBody
    @GetMapping("/{userIdx}/{petIdx}")
    public BaseResponse<List<GetReadyRes>> getReady(@PathVariable("userIdx") int userIdx, @PathVariable("petIdx") int petIdx){
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            // 같다면
            List<GetReadyRes> getReadyRes = readyProvider.getReady(userIdx, petIdx);
            return new BaseResponse<>(getReadyRes);

        }  catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 준비하기 개별 질문 조회
     * [GET] /ready/:userIdx/:readyAnswerIdx
     * @return BaseResponse<List<GetOneReadyRes>>
     * */
    @ResponseBody
    @GetMapping("/{userIdx}/{readyAnswerIdx}")
    public BaseResponse<List<GetOneReadyRes>> getOneReady(@PathVariable("userIdx") int userIdx, @PathVariable("readyAnswerIdx") int readyAnswerIdx){
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            // 같다면
            List<GetOneReadyRes> getOneReadyRes = readyProvider.getOneReady(userIdx, readyAnswerIdx);
            return new BaseResponse<>(getOneReadyRes);

        }  catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
