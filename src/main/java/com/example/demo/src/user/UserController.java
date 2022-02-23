package com.example.demo.src.user;

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
    public BaseResponse<UserDto> getUser(@PathVariable("userIdx") Integer userIdx) {
        try{
            UserDto result = userProvider.getUser(userIdx);
            if (result == null){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<ResponseUser> createUser(@RequestBody SaveUserDto saveUserDto) {
        try{
            if (saveUserDto.getId() == null){
                return new BaseResponse<>(NONE_ID_EXIST);
            }

            if (saveUserDto.getPassword() == null){
                return new BaseResponse<>(NONE_PASSWORD_EXIST);
            }

            ResponseUser result = userService.createUser(saveUserDto);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<ResponseUser> login(@RequestBody SaveUserDto saveUserDto) {
        try{
            if (saveUserDto.getId() == null){
                return new BaseResponse<>(NONE_ID_EXIST);
            }

            if (saveUserDto.getPassword() == null){
                return new BaseResponse<>(NONE_PASSWORD_EXIST);
            }

            ResponseUser result = userProvider.login(saveUserDto);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/book")
    public BaseResponse<String> login(@RequestBody SaveBookDto saveBookDto) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(!userProvider.chkUser(userIdxByJwt)){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            if (saveBookDto.getTitle() == null){
                return new BaseResponse<>(NONE_TITLE_EXIST);
            }

            if (saveBookDto.getNickname() == null){
                return new BaseResponse<>(NONE_NICKNAME_EXIST);
            }
            userService.createBook(saveBookDto, userIdxByJwt);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> modifyPassword(@RequestBody SaveUserDto user){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if (user.getId() == null){
                return new BaseResponse<>(NONE_ID_EXIST);
            }

            if (user.getPassword() == null){
                return new BaseResponse<>(NONE_PASSWORD_EXIST);
            }

            if (user.getUpdatepassword() == null){
                return new BaseResponse<>(NONE_PASSWORD_EXIST);
            }

            if (!userService.modifyPassword(user)){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/pet")
    public BaseResponse<List<PetDto>> getPetLists() {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(!userProvider.chkUser(userIdxByJwt)){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            List<PetDto> resultList = userProvider.getPetbyId(userIdxByJwt);
            return new BaseResponse<>(resultList);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/pet")
    public BaseResponse<List<PetDto>> getPetLists(@RequestBody PetDto pet) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(!userProvider.chkUser(userIdxByJwt)){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            if (pet.getName() == null){
                return new BaseResponse<>(NONE_NAME_EXIST);
            }

            if (pet.getImgurl() == null){
                return new BaseResponse<>(NONE_IMG_EXIST);
            }

            if (pet.getType() == null){
                return new BaseResponse<>(NONE_TYPE_EXIST);
            }

            if (pet.getBirth() == null){
                return new BaseResponse<>(NONE_BIRTH_EXIST);
            }

            List<PetDto> result  = userService.createPet(pet,userIdxByJwt);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/pet/{petIdx}")
    public BaseResponse<PetDto> modifyPetLists(@PathVariable("petIdx") int petIdx, @RequestBody PetDto pet) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(!userProvider.chkUser(userIdxByJwt)){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            PetDto result = userService.updatePet(pet, petIdx, userIdxByJwt);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @DeleteMapping("/pet/{petIdx}")
    public BaseResponse<String> deletePet(@PathVariable("petIdx") int petIdx) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(!userProvider.chkUser(userIdxByJwt)){
                return new BaseResponse<>(NONE_USER_EXIST);
            }

            userService.deletePet(petIdx,userIdxByJwt);
            return new BaseResponse<>("");
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
