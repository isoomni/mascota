package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int idx;
    private String id;
    private String nickname;
    private int level;
    private String imgUrl;
    private String title;
}
