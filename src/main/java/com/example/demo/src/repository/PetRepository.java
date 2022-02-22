<<<<<<< HEAD:src/main/java/com/example/demo/src/user/PetRepository.java
package com.example.demo.src.user;

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

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByUser(User user);
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

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByUserAndStatus(User user, String status);

    Optional<Pet> findByUserAndName(User user, String name);
>>>>>>> fe947a315c6d9c4aeffaccd472c120edea4514ad:src/main/java/com/example/demo/src/repository/PetRepository.java
}