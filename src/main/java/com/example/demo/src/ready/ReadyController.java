package com.example.demo.src.ready;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.ready.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/readies")
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
     * [GET] /readies/all/:userIdx/:petIdx
     * @return BaseResponse<List<GetReadyRes>>
     * */
    @ResponseBody
    @GetMapping("/all/{userIdx}/{petIdx}")
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
     * [GET] /readies/one/:userIdx/:readyAnswerIdx
     * @return BaseResponse<List<GetOneReadyRes>>
     * */
    @ResponseBody
    @GetMapping("/one/{userIdx}/{readyAnswerIdx}")
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

    /**
     * 준비하기 개별 답변 작성 API
     * [POST] /readies/one/answer/:userIdx/:petIdx/:readyQuestionIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping ("/one/answer/{userIdx}/{petIdx}/{readyQuestionIdx}")
    public BaseResponse<String> createReadyAnswer(@PathVariable("userIdx") int userIdx,@PathVariable("petIdx") int petIdx,@PathVariable("readyQuestionIdx") int readyQuestionIdx, @RequestBody PostReadyAnswer postReadyAnswer){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            //같다면 유저네임 변경
            PostReadyAnswerReq postReadyAnswerReq = new PostReadyAnswerReq(petIdx,readyQuestionIdx, postReadyAnswer.getContext());
            readyService.createReadyAnswer(postReadyAnswerReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 준비하기 개별 답변 수정 API
     * [PATCH] /readies/one/answer/:userIdx/:readyAnswerIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/one/answer/{userIdx}/{readyAnswerIdx}")
    public BaseResponse<String> modifyReadyAnswer(@PathVariable("userIdx") int userIdx,@PathVariable("readyAnswerIdx") int readyAnswerIdx, @RequestBody PatchReadyAnswer patchReadyAnswer){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            //같다면 유저네임 변경
            PatchReadyAnswerReq patchReadyAnswerReq = new PatchReadyAnswerReq(readyAnswerIdx, patchReadyAnswer.getContext());
            readyService.modifyReadyAnswer(patchReadyAnswerReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        logger.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
        logger.warn("Warn Level 테스트");
//        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
//        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
        logger.error("ERROR Level 테스트");

        return "Success Test";
    }


}
