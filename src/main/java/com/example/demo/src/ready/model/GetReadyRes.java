package com.example.demo.src.ready.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetReadyRes {
    private int questionNum;
    private String question;
    private String existenceOfAnswer;
}
