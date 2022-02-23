package com.example.demo.src.memory.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostMemoryAnswerReq {
    private int petIdx;
    private int memoryQuestionIdx;
    private String context;
}
