package com.example.demo.src.my;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.my.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;


@RestController
@RequestMapping("/myPages")
public class MyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MyProvider myProvider;
    @Autowired
    private final MyService myService;
    @Autowired
    private final JwtService jwtService;

    public MyController(MyProvider myProvider, MyService myService, JwtService jwtService){
        this.myProvider = myProvider;
        this.myService = myService;
        this.jwtService = jwtService;
    }

    /**
     * 마이페이지 전체 조회
     * [GET] /myPages/:userIdx
     * @return BaseResponse<List<GetMyRes>>
     * */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<GetMyPageRes> getMyPage(@PathVariable("userIdx") int userIdx){
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            // 같다면
            GetMyPageRes getMyPageRes = myProvider.getMyPage(userIdx);
            return new BaseResponse<>(getMyPageRes);

        }  catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 마이페이지 개인정보 조회
     * [GET] /myPages/myInfo/:userIdx
     * @return BaseResponse<GetMyInfoRes>
     * */
    @ResponseBody
    @GetMapping("/myInfo/{userIdx}")
    public BaseResponse<GetMyInfoRes> getMyInfo(@PathVariable("userIdx") int userIdx){
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            // 같다면
            GetMyInfoRes getMyInfoRes = myProvider.getMyInfo(userIdx);
            return new BaseResponse<>(getMyInfoRes);

        }  catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 마이페이지 비밀번호 변경
     * [PATCH] /myPages/myInfo/password/:userIdx
     * @return BaseResponse<String>
     * */
    @ResponseBody
    @PatchMapping("/myInfo/password/{userIdx}")
    public BaseResponse<String> modifyPassword(@PathVariable("userIdx") int userIdx, @RequestBody MyPassword myPassword){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(myPassword.getOldPassword() == null){
                return new BaseResponse<>(EMPTY_USER_OLD_PASSWORD); // 현재 비밀번호
            }
            if(myPassword.getNewPassword() == null){
                return new BaseResponse<>(EMPTY_USER_NEW_PASSWORD);  // 신규 비밀번호
            }
            //비밀번호 정규표현
            if (!isRegexPassword(myPassword.getNewPassword())){  // 특수문자 / 문자 / 숫자 포함 형태의 8~20자리 이내의 암호 정규식
                return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
            }
            if(myPassword.getReNewPassword() == null){
                return new BaseResponse<>(EMPTY_USER_NEW_PASSWORD);  // 신규 비밀번호 재확인
            }
            if(!myPassword.getNewPassword().equals(myPassword.getReNewPassword())){
                return new BaseResponse<>(REENTER_PASSWORD_IS_DIFFERENT); // 재확인 비밀번호가 일치하지 않습니다.
            }

            PatchMyPasswordReq patchMyPasswordReq = new PatchMyPasswordReq(userIdx, myPassword.getOldPassword(), myPassword.getNewPassword(), myPassword.getReNewPassword());
            myService.modifyPassword(patchMyPasswordReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /**
     * 책 표지 수정
     * [PATCH] /myPages/bookInfo/:userIdx
     * @return BaseResponse<String>
     * */
    @ResponseBody
    @PatchMapping("/bookInfo/{userIdx}")
    public BaseResponse<String> modifyMyBook(@PathVariable("userIdx") int userIdx, @RequestBody MyBook myBook){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            if(myBook.getBookTitle() == null){
                return new BaseResponse<>(EMPTY_BOOK_TITLE);
            }
            if(myBook.getUserNickname() == null){
                return new BaseResponse<>(EMPTY_WRITER_NICKNAME);
            }

            // 책 제목 길이 정규표현 - 12자
            if(!isRegexTextLengthTwelve(myBook.getBookTitle())){
                return new BaseResponse<>(OVER_TWELVE_CHAR);
            }
            // 작가명 길이 정규표현 - 12자
            if(!isRegexTextLengthTwelve(myBook.getUserNickname())){
                return new BaseResponse<>(OVER_TWELVE_CHAR);
            }

            PatchMyBookReq patchMyBookReq = new PatchMyBookReq(userIdx, myBook.getBookImgUrl(), myBook.getBookTitle(), myBook.getUserNickname());
            myService.modifyMyBook(patchMyBookReq);

            String result = "";
            return new BaseResponse<>(result);

        } catch (BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 반려동물 프로필 수정
     * [PATCH] /myPages/petInfo/:userIdx/:petIdx
     * @return BaseResponse<String>
     * */
    @ResponseBody
    @PatchMapping("/petInfo/{userIdx}/{petIdx}")
    public BaseResponse<String> modifyMyPet(@PathVariable("userIdx") int userIdx,@PathVariable("petIdx") int petIdx, @RequestBody MyPet myPet){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            if(myPet.getPetName() == null){
                return new BaseResponse<>(EMPTY_PET_NAME);
            }
            if(myPet.getPetType() == null){
                return new BaseResponse<>(EMPTY_PET_TYPE);
            }
            if(myPet.getPetBecomeFamilyDay() == null){
                return new BaseResponse<>(EMPTY_PET_FAMILY_DATE);
            }

            // 반려동물 이름 길이 정규표현 - 6자
            if(!isRegexTextLengthSix(myPet.getPetName())){
                return new BaseResponse<>(OVER_SIX_CHAR);
            }
            PatchMyPetReq patchMyPetReq = new PatchMyPetReq(petIdx, myPet.getPetImgUrl(), myPet.getPetName(), myPet.getPetType(), myPet.getPetBecomeFamilyDay());
            myService.modifyMyPet(patchMyPetReq);

            String result = "";
            return new BaseResponse<>(result);

        } catch (BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 동물 프로필 삭제 API
     * [PATCH] /myPages/petInfo/:userIdx/:petIdx/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/petInfo/{userIdx}/{petIdx}/status") // (PATCH) 127.0.0.1:9090/app/restaurants/:userIdx/reviews/:reviewIdx/status
    public BaseResponse<String> deleteMyPet(@PathVariable("userIdx") int userIdx, @PathVariable("petIdx") int petIdx, @RequestBody PetStatus petStatus){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            if(petStatus.getPetStatus() == null){
                return new BaseResponse<>(EMPTY_PET_STATUS);
            }
            PatchPetStatusReq patchPetStatusReq = new PatchPetStatusReq(petIdx, petStatus.getPetStatus());
            myService.deleteMyPet(patchPetStatusReq);

            String result = "";
            return new BaseResponse<>(result);

        } catch (BaseException exception){
            exception.printStackTrace();
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
