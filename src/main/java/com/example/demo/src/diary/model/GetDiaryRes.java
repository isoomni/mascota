package com.example.demo.src.diary.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDiaryRes {
    private int idx;
    private String list;
    private String title;
    private String context;
    private String type;
    private String date;
    private String day;
    private String imgUrl;
}
