package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexDate;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/{userIdx}") 
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
        try{
            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostLoginRes> createUser(@RequestBody PostLoginReq postUserReq) {
        try{
            if (postUserReq.getId() == null){
                return new BaseResponse<>(NONE_ID_EXIST);
            }

            if (userProvider.checkId(postUserReq.getId()) == 1){
                return new BaseResponse<>(ALREADY_USER_EXIST);
            }

            if (postUserReq.getPassword() == null){
                return new BaseResponse<>(NONE_PASSWORD_EXIST);
            }

            if (userProvider.checkId(postUserReq.getId()) == 1){
                return new BaseResponse<>(POST_USERS_EXISTS_ID);
            }

            PostLoginRes postLoginRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postLoginRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            if (postLoginReq.getId() == null){
                return new BaseResponse<>(NONE_ID_EXIST);
            }

            if (userProvider.checkId(postLoginReq.getId()) == 0){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            if (postLoginReq.getPassword() == null){
                return new BaseResponse<>(NONE_PASSWORD_EXIST);
            }

            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody PatchUserReq user){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            if(userProvider.checkUser(userIdxByJwt) == 0){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            if (user.getNickname() == null){
                return new BaseResponse<>(CHANGE_NICKNAME_EXIST);
            }

            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getNickname());
            userService.modifyUserName(patchUserReq);

            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/pet")
    public BaseResponse<List<Pet>> getPetLists() {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userProvider.checkUser(userIdxByJwt) == 0){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            List<Pet> resultList = userProvider.getPetbyId(userIdxByJwt);
            return new BaseResponse<>(resultList);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/pet")
    public BaseResponse<String> createPet(@RequestBody Pet pet){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userProvider.checkUser(userIdxByJwt) == 0){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            if (pet.getName() == null){
                return new BaseResponse<>(NONE_NAME_EXIST);
            }

            if (pet.getImgUrl() == null){
                return new BaseResponse<>(NONE_IMG_EXIST);
            }

            if (pet.getType() == null){
                return new BaseResponse<>(NONE_TYPE_EXIST);
            }

            if (pet.getBirth() == null){
                return new BaseResponse<>(NONE_BIRTH_EXIST);
            }

            if (!isRegexDate(pet.getBirth())){
                return new BaseResponse<>(POST_USERS_INVALID_DATE);
            }

            userService.createPet(pet, userIdxByJwt);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/pet/{petIdx}")
    public BaseResponse<String> updatePet(@PathVariable("petIdx") int petIdx, @RequestBody Pet pet){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userProvider.checkUser(userIdxByJwt) == 0){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            if (pet.getName() == null && pet.getImgUrl() == null && pet.getType() == null && pet.getBirth() == null){
                return new BaseResponse<>(NONE_UPDATE_EXIST);
            }

            if (pet.getBirth() != null && !isRegexDate(pet.getBirth())){
                return new BaseResponse<>(POST_USERS_INVALID_DATE);
            }

            userService.updatePet(pet, petIdx);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/pet/del/{petIdx}")
    public BaseResponse<String> deletePet(@PathVariable("petIdx") int petIdx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userProvider.checkUser(userIdxByJwt) == 0){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            if(userProvider.checkPet(petIdx) == 0){
                return new BaseResponse<>(NONE_PET_EXIST);
            }

            userService.deletePet(petIdx);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/book")
    public BaseResponse<String> createPet(@RequestBody PostUserReq postUserReq){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userProvider.checkUser(userIdxByJwt) == 0){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            if (postUserReq.getTitle() == null){
                return new BaseResponse<>(NONE_TITLE_EXIST);
            }

            if (postUserReq.getNickname() == null){
                return new BaseResponse<>(NONE_NICKNAME_EXIST);
            }

            userService.createBook(postUserReq, userIdxByJwt);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/book")
    public BaseResponse<Book> getBook(){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userProvider.checkUser(userIdxByJwt) == 0){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            Book result = userProvider.selectBook(userIdxByJwt);

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}
