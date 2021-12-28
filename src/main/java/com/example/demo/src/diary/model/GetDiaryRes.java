package com.example.demo.src.diary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetDiaryRes {
    private int idx;
    private String list;
    private String title;
    private String context;
    private String type;
    private String date;
    private String day;
    private String imgUrl;

    public GetDiaryRes(int idx, String context, String date, String imgUrl){
        this.idx = idx;
        this.context = context;
        this.date = date;
        this.imgUrl = imgUrl;
    }
}
