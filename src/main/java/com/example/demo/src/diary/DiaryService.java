package com.example.demo.src.diary;

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

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
@Transactional
public class DiaryService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DiaryProvider diaryProvider;
    private final JwtService jwtService;

    @Autowired
    DiaryListRepository diaryListRepository;

    @Autowired
    DiaryImgRepository diaryImgRepository;

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    MoodRepository moodRepository;

    @Autowired
    public DiaryService(DiaryProvider diaryProvider, JwtService jwtService) {
        this.diaryProvider = diaryProvider;
        this.jwtService = jwtService;
    }

    public void insertDiaryList(DiaryListDto diaryListDto, Integer userIdx, Integer type) throws BaseException {
        try{
            User user = new User(userIdx);
            List<DiaryList> result = diaryListRepository.findByUserAndTypeOrderByNumAsc(user, type);
            for (DiaryList d : result){
                if (d.getContext().equals(diaryListDto.getContext())){
                    throw new BaseException(FAIL_LISTS_ADD);
                }
            }
            Integer number = 1;
            if (result.size() != 0){
                number = result.get(result.size()-1).getNum() + 1;
            }
            DiaryList insert = new DiaryList(user, diaryListDto, number, type);
            diaryListRepository.save(insert);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateDiaryList(List<String> diaryListDto, Integer userIdx, Integer type) throws BaseException {
        try{
            User user = new User(userIdx);
            List<DiaryList> result = diaryListRepository.findByUserAndTypeOrderByNumAsc(user, type);

            if (result.size() != diaryListDto.size()){
                throw new BaseException(FAIL_LISTS_ADD);
            }
            int cnt = 1;
            for (DiaryList d : result){
                d.setContext(diaryListDto.get(cnt-1));
                d.setNum(cnt);
                cnt++;
            }
            diaryListRepository.saveAll(result);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteDiaryList(Integer listIdx, Integer userIdx) throws BaseException {
        try{
            Optional<DiaryList> result = diaryListRepository.findById(listIdx);
            if (result.isPresent()) {
//                diaryRepository.deleteByDiaryList(result.get());
                diaryListRepository.deleteById(listIdx);
            }
            else {
                throw new BaseException(FAIL_LISTS_DEL);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void insertDiary(Integer listIdx, DiaryDto diaryDto, Integer userIdx) throws BaseException {
        try{
            User user = new User(userIdx);
            Optional<DiaryList> result = diaryListRepository.findById(listIdx);
            DiaryList nowList = new DiaryList();
            if (result.isPresent()) {
                nowList = result.get();
            }
            else {
                throw new BaseException(FAIL_LISTS_DEL);
            }
            Diary insert = diaryRepository.save(new Diary(user,nowList,diaryDto));
            List<DiaryImg> imgs = new ArrayList<>();
            for (String d : diaryDto.getImgurls()){
                imgs.add(new DiaryImg(insert, d));
            }
            diaryImgRepository.saveAll(imgs);
            List<Mood> moods = new ArrayList<>();
            for (MoodDto d : diaryDto.getMoods()){
                moods.add(new Mood(insert, d));
            }
            moodRepository.saveAll(moods);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteDiary(Integer diaryIdx, Integer userIdx) throws BaseException {
        try{
            Optional<Diary> chk = diaryRepository.selectById(diaryIdx);
            if (chk.isPresent()){
                Diary result = chk.get();
                User s = result.getUser();
                if (s.getIdx() != userIdx){
                    throw new BaseException(NONE_DIARY_WRITE);
                }
                diaryRepository.deleteById(diaryIdx);
            }
            else{
                throw new BaseException(NONE_DIARY_EXIST);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateDiary(Integer diaryIdx, DiaryDto diaryDto, Integer userIdx) throws BaseException {
        try{
            Optional<Diary> chk = diaryRepository.findById(diaryIdx);
            if (chk.isPresent()){
                Diary result = chk.get();
                diaryRepository.save(result);
                Set<DiaryImg> imgs = result.getImgurls();
                Set<Mood> moods = result.getMoods();
                List<DiaryImgDto> imgurls = new ArrayList<>();
                List<MoodDto> updateMoods = new ArrayList<>();
                List<DiaryImg> delImgs = new ArrayList<>();
                List<Mood> delMoods = new ArrayList<>();
                for (DiaryImg i : imgs){
                    imgurls.add(new DiaryImgDto(i));
                }
                for (Mood m : moods){
                    updateMoods.add(new MoodDto(m));
                }
                Collections.sort(imgurls);
                Collections.sort(updateMoods);
                imgs.clear();
                moods.clear();
                result.setByDiaryDto(diaryDto); // 내용
                if (imgurls.size() <= diaryDto.getImgurls().size()){
                    for (int i = 0; i < imgurls.size(); i++){
                        imgurls.get(i).setImgurl(diaryDto.getImgurls().get(i));
                        imgs.add(new DiaryImg(result,imgurls.get(i)));
                    }
                    for (int i = imgurls.size(); i < diaryDto.getImgurls().size(); i++){
                        imgs.add(new DiaryImg(result,diaryDto.getImgurls().get(i)));
                    }
                }
                else{
                    for (int i = 0; i < diaryDto.getImgurls().size(); i++){
                        imgurls.get(i).setImgurl(diaryDto.getImgurls().get(i));
                        imgs.add(new DiaryImg(result,imgurls.get(i)));
                    }
                    for (int i = diaryDto.getImgurls().size(); i < imgurls.size(); i++){
                        delImgs.add(new DiaryImg(result,imgurls.get(i)));
                    }
                }
                diaryImgRepository.saveAll(imgs);
                diaryImgRepository.deleteAll(delImgs);
                if (updateMoods.size() <= diaryDto.getMoods().size()){
                    for (int i = 0; i < updateMoods.size(); i++){
                        updateMoods.get(i).setName(diaryDto.getMoods().get(i).getName());
                        updateMoods.get(i).setType(diaryDto.getMoods().get(i).getType());
                        moods.add(new Mood(result,updateMoods.get(i)));
                    }
                    for (int i = updateMoods.size(); i < diaryDto.getMoods().size(); i++){
                        moods.add(new Mood(result,diaryDto.getMoods().get(i)));
                    }
                }
                else{
                    for (int i = 0; i < diaryDto.getMoods().size(); i++){
                        updateMoods.get(i).setName(diaryDto.getMoods().get(i).getName());
                        updateMoods.get(i).setType(diaryDto.getMoods().get(i).getType());
                        moods.add(new Mood(result,updateMoods.get(i)));
                    }
                    for (int i = diaryDto.getMoods().size(); i < updateMoods.size(); i++){
                        delMoods.add(new Mood(result,updateMoods.get(i)));
                    }
                }
                moodRepository.saveAll(moods);
                moodRepository.deleteAll(delMoods);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
