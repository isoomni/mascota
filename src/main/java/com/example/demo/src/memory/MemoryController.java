package com.example.demo.src.memory;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.memory.model.*;
import com.example.demo.src.ready.model.PatchReadyAnswer;
import com.example.demo.src.ready.model.PatchReadyAnswerReq;
import com.example.demo.src.ready.model.PostReadyAnswer;
import com.example.demo.src.ready.model.PostReadyAnswerReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/memories")
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
     * [GET] /memories/all/notAnsweredMemories/:userIdx/:petIdx
     * @return BaseResponse<List<GetNotAnsweredMemoryRes>>
     * */
    @ResponseBody
    @GetMapping("/all/notAnsweredMemories/{userIdx}/{petIdx}")
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
     * [GET] /memories/all/answeredMemories/:userIdx/:petIdx
     * @return BaseResponse<List<GetAnsweredMemoryRes>>
     * */
    @ResponseBody
    @GetMapping("/all/answeredMemories/{userIdx}/{petIdx}")
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
     * [GET] /memories/one/:userIdx/:readyAnswerIdx
     * @return BaseResponse<List<GetOneMemoryRes>>
     * */
    @ResponseBody
    @GetMapping("/one/{userIdx}/{memoryAnswerIdx}")
    public BaseResponse<List<GetOneMemoryRes>> getOneMemory(@PathVariable("userIdx") int userIdx, @PathVariable("memoryAnswerIdx") int memoryAnswerIdx){
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            // 같다면
            List<GetOneMemoryRes> getOneMemoryRes = memoryProvider.getOneMemory(userIdx, memoryAnswerIdx);
            return new BaseResponse<>(getOneMemoryRes);

        }  catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 추억하기 개별 답변 작성 API
     * [PATCH] /memories/one/:userIdx/:petIdx/:memoryQuestionIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/one/{userIdx}/{petIdx}/{memoryQuestionIdx}")
    public BaseResponse<String> createMemoryAnswer(@PathVariable("userIdx") int userIdx,@PathVariable("petIdx") int petIdx,@PathVariable("memoryQuestionIdx") int memoryQuestionIdx, @RequestBody PostMemoryAnswer postMemoryAnswer){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            //같다면 유저네임 변경
            PostMemoryAnswerReq postMemoryAnswerReq = new PostMemoryAnswerReq(petIdx,memoryQuestionIdx, postMemoryAnswer.getContext());
            memoryService.createMemoryAnswer(postMemoryAnswerReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 추억하기 개별 답변 수정 API
     * [PATCH] /memories/one/:userIdx/:memoryAnswerIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/one/{userIdx}/{memoryAnswerIdx}")
    public BaseResponse<String> modifyMemoryAnswer(@PathVariable("userIdx") int userIdx,@PathVariable("memoryAnswerIdx") int memoryAnswerIdx, @RequestBody PatchMemoryAnswer patchMemoryAnswer){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            //같다면 유저네임 변경
            PatchMemoryAnswerReq patchMemoryAnswerReq = new PatchMemoryAnswerReq(userIdx,memoryAnswerIdx, patchMemoryAnswer.getContext());
            memoryService.modifyMemoryAnswer(patchMemoryAnswerReq);

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
