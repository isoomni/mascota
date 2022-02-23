package com.example.demo.src.memory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOneMemoryRes {
    private int questionNum;
    private String question;
    private String answer;
    private String updatedAt;
}
