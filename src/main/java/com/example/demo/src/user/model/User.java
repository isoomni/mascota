package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userIdx;
    private String id;
    private String nickname;
    private String password;
    private String status;
    private int level;
    private String imgUrl;
    private String authorName;
    private String prologTitle;

    public User(int userIdx, String password){
        this.userIdx = userIdx;
        this.password = password;
    }

}
