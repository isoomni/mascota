package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

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
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:9000/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
        // Get Users
        try{
            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostLoginRes> createUser(@RequestBody PostUserReq postUserReq) {
        try{
            if (postUserReq.getId() == null){
                return new BaseResponse<>(NONE_ID_EXIST);
            }
            if (postUserReq.getPassword() == null){
                return new BaseResponse<>(NONE_PASSWORD_EXIST);
            }

            if (postUserReq.getNickname() == null){
                return new BaseResponse<>(NONE_NICKNAME_EXIST);
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
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            if (user.getNickname() == null){
                return new BaseResponse<>(CHANGE_NICKNAME_EXIST);
            }

            //같다면 유저네임 변경
            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getNickname());
            userService.modifyUserName(patchUserReq);

            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
