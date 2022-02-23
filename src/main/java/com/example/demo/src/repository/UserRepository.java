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

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM user as u WHERE u.id = :id")
    Optional<User> selectById(@Param("id") String id);

    @EntityGraph(attributePaths = "pets")
    @Query("select u from user u where u.idx = :idx")
    Optional<User> findByIdx(Integer idx);
}