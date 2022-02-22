package com.example.demo.src.repository;

import com.example.demo.src.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public interface MoodRepository extends JpaRepository<Mood, Integer> {

    @Modifying
    @Query(value = "update mood join diary on mood.diary_id = diary.idx set mood.name = :cur where diary.user_id = :userid and mood.name = :prev", nativeQuery=true)
    void changeMood(@Param(value = "cur") String cur, @Param(value = "userid") Integer userid, @Param(value = "prev") String prev);

}