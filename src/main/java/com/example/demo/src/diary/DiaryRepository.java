package com.example.demo.src.diary;

import com.example.demo.src.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {

    @EntityGraph(attributePaths = {"moods","imgurls"},type = EntityGraphType.LOAD)
    Optional<Diary> findById(Integer idx);

    @Query(value = "SELECT DISTINCT d from mood as m INNER JOIN m.diary d WHERE m.name = :name AND d.user = :user")
    List<Diary> findByNameAndUser(@Param(value = "name") String name, @Param(value = "user") User user);

    @Query(value = "DELETE FROM diary d WHERE d.diaryList = :diaryList")
    void deleteByDiaryList(@Param(value = "diaryList") DiaryList diaryList);

    int countByDiaryList(DiaryList diaryList);

    int countByUser(User user);

    @Query(value = "SELECT new com.example.demo.src.model.DiarySummary(d.idx,d.title, d.context, d.date, i.imgurl)" +
            " FROM diaryimg i INNER JOIN i.diary d WHERE d.diaryList = :diaryList GROUP BY d.idx ORDER BY d.date DESC")
    List<DiarySummary> findByDiaryList(@Param(value = "diaryList") DiaryList diaryList, Pageable pageable);

    @Query(value = "SELECT new com.example.demo.src.model.DiarySummary(d.idx,d.title, d.context, d.date, i.imgurl)" +
            " FROM diaryimg i INNER JOIN i.diary d WHERE d.user = :user GROUP BY d.idx")
    List<DiarySummary> findByUser(@Param(value = "user") User user, Pageable pageable);

    @Query("SELECT d FROM diary as d WHERE d.idx = :idx")
    Optional<Diary> selectById(@Param("idx") Integer idx);


}