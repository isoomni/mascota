package com.example.demo.src.ready.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOneReadyRes {
    private int questionNum;
    private String question;
    private String answer;
    private String updatedAt;

}
