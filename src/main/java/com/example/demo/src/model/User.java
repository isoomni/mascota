package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(length = 100, nullable = false, unique = true)
    private String id;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 255, nullable = true)
    private String nickname;

    private String status = "N";

    private Integer level = 1;

    @Column(length = 2000, nullable = true)
    private String imgurl;

    @Column(length = 45, nullable = true)
    private String title;

    @Transient
    private String jwt;

    @OneToMany(mappedBy="user")
    List<Pet> pets = new ArrayList<Pet>();

    @Builder
    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @Builder
    public User(Integer idx) {
        this.idx = idx;
    }

    public void setBook(SaveBookDto save) {
        this.nickname = save.getNickname();
        this.imgurl = save.getImgurl();
        this.title = save.getTitle();
    }

    public void addPet(Pet pet){
        this.pets.add(pet);
        pet.setUser(this);
    }

}
