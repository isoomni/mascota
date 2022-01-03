package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import javax.sql.DataSource;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
@Transactional
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    public UserService(UserProvider userProvider, JwtService jwtService) {
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    public UserDto createUser(SaveUserDto user) throws BaseException {
        try{
            String pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(user.getPassword());
            user.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        try{
            User saveUser = userRepository.save(user.toEntity());
            UserDto result = new UserDto(saveUser);
            String jwt = jwtService.createJwt(saveUser.getIdx());
            result.setJwt(jwt);
            return result;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createBook(SaveBookDto saveBookDto, Integer userIdx) throws BaseException {
        try{
            Optional<User> result = userRepository.findById(userIdx);
            if (result.isPresent()) {
                User user = result.get();
                user.setBook(saveBookDto);
                userRepository.save(user);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean modifyPassword(SaveUserDto saveUserDto) throws BaseException {
        String confirm, newPassword;
        try{
            confirm = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(saveUserDto.getPassword());
            newPassword = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(saveUserDto.getUpdatepassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        try{
            Optional<User> result = userRepository.findById(saveUserDto.getId());
            if (result.isPresent()) {
                User user = result.get();
                if (!user.getPassword().equals(confirm)){
                    return false;
                }
                else{
                    user.setPassword(newPassword);
                    userRepository.save(user);
                    return true;
                }
            }
            return false;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PetDto> createPet(PetDto pet, Integer userIdx) throws BaseException {
        try{
            Optional<User> result = userRepository.findByIdx(userIdx);
            if (result.isPresent()) {
                User user = result.get();
                int init = user.getPets().size();
                Pet updatePet = new Pet(user, pet);
                boolean flag = false;
                for (Pet p : user.getPets()){
                    if (p.getName().equals(updatePet.getName()) && p.getType().equals(updatePet.getType())){
                        flag = true;
                        break;
                    }
                }
                if (!flag){
                    user.addPet(updatePet);
                    petRepository.save(updatePet);
                }
                List<PetDto> answer = new ArrayList<>();
                user.getPets().forEach(p -> {
                    answer.add(new PetDto(p));
                });
                return answer;
            }
            throw new BaseException(DATABASE_ERROR);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PetDto updatePet(PetDto pet, Integer petIdx) throws BaseException {
        try{
            Optional<Pet> result = petRepository.findById(petIdx);
            if (result.isPresent()) {
                Pet now = result.get();
                if (pet.getImgurl() != null){
                    now.setImgurl(pet.getImgurl());
                }
                if (pet.getName() != null){
                    now.setName(pet.getName());
                }
                if (pet.getType() != null){
                    now.setType(pet.getType());
                }
                if (pet.getBirth() != null){
                    now.setBirth(pet.getBirth());
                }
                petRepository.save(now);
                return new PetDto(now);
            }
            throw new BaseException(DATABASE_ERROR);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deletePet(Integer petIdx) throws BaseException {
        try{
            Optional<Pet> result = petRepository.findById(petIdx);
            if (result.isPresent()) {
                Pet now = result.get();
                petRepository.deleteById(petIdx);
            }
            else {
                throw new BaseException(NONE_PET_EXIST);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
