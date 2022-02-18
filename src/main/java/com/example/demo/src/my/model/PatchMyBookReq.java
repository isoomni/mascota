package com.example.demo.src.my.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchMyBookReq {
    private int userIdx;
    private String bookImgUrl;
    private String bookTitle;
    private String userNickname;
}
