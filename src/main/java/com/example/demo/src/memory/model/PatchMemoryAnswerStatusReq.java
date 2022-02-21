package com.example.demo.src.memory.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchMemoryAnswerStatusReq {
    private int memoryAnswerIdx;
    private String status;
}
