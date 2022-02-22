package com.example.demo.src.specification;

import org.springframework.data.jpa.domain.Specification;
import com.example.demo.src.model.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import java.time.LocalDateTime;

public class DiarySpecification {

    public static Specification<Diary> chkMyDiary(Integer idx) {
        return new Specification<Diary>() {
            @Override
            public Predicate toPredicate(Root<Diary> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder builder){
                return builder.equal(root.get("idx"),idx);
            }
        };
    }

    public static Specification<Diary> countByDiaryList(DiaryList diaryList) {
        return new Specification<Diary>() {
            @Override
            public Predicate toPredicate(Root<Diary> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder builder){

                return builder.equal(root.get("diaryList"),diaryList);
            }
        };
    }

    public static Specification<Diary> countByUser(User user) {
        return new Specification<Diary>() {
            @Override
            public Predicate toPredicate(Root<Diary> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder builder){

                return builder.equal(root.get("user"),user);
            }
        };
    }
    

}