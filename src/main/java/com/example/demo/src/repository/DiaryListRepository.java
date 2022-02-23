package com.example.demo.src.repository;

import com.example.demo.src.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public interface DiaryListRepository extends JpaRepository<DiaryList, Integer> {
    List<DiaryList> findByUserAndTypeOrderByNumAsc(User user, Integer type);

    Optional<DiaryList> findTopByUserAndTypeOrderByNumAsc(User user, Integer type);

}