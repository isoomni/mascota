package com.example.demo.src.ready.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchReadyAnswerReq {
    private int userIdx;
    private int readyAnswerIdx;
    private String context;
}
