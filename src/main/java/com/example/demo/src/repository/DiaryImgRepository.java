<<<<<<< HEAD:src/main/java/com/example/demo/src/diary/DiaryImgRepository.java
package com.example.demo.src.diary;

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

public interface DiaryImgRepository extends JpaRepository<DiaryImg, Integer> {

    
=======
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

public interface DiaryImgRepository extends JpaRepository<DiaryImg, Integer> {

    
>>>>>>> fe947a315c6d9c4aeffaccd472c120edea4514ad:src/main/java/com/example/demo/src/repository/DiaryImgRepository.java
}