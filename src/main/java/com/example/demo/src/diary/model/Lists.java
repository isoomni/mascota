package com.example.demo.src.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Lists {
    private int listIdx;
    private String context;
    private String status;

    public Lists(int listIdx, String context){
        this.listIdx = listIdx;
        this.context = context;
        this.status = "N";
    }
}
