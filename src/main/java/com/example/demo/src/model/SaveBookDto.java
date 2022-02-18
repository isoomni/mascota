package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@NoArgsConstructor
@Getter
@Setter
public class SaveBookDto{
    private String nickname;
    private String imgurl;
    private String title;

    @Builder
    public SaveBookDto(String nickname, String imgurl, String title){
        this.nickname = nickname;
        this.imgurl = imgurl;
        this.title = title;
    }
}