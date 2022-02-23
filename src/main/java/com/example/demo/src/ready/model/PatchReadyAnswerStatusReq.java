package com.example.demo.src.ready.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchReadyAnswerStatusReq {
    private int readyAnswerIdx;
    private String status;
}
