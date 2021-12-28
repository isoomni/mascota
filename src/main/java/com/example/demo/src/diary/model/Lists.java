package com.example.demo.src.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Lists {
    private int listIdx;
    private int userIdx;
    private String context;
    private String status;

    public Lists(int listIdx, String context){
        this.listIdx = listIdx;
        this.context = context;
    }
}
