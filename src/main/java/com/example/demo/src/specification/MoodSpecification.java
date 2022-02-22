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

public class MoodSpecification {

    public static Specification<Mood> chkMyMood(String name) {
        return new Specification<Mood>() {
            @Override
            public Predicate toPredicate(Root<Mood> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder builder){

                return builder.equal(root.get("name"),name);
            }
        };
    }


}