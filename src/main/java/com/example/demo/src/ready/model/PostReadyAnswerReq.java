package com.example.demo.src.ready.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReadyAnswerReq {
    private int petIdx;
    private int readyQuestionIdx;
    private String context;
}
