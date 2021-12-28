package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private int userIdx;
    private String id;
    private String nickname;
    private String password;
    private String status;
    private int level;
    private String imgUrl;
    private String authorName;
    private String title;

    public User(int userIdx, String password){
        this.userIdx = userIdx;
        this.password = password;
    }

    public User(String nickname, String imgUrl, String title){
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.title = title;
    }

}
