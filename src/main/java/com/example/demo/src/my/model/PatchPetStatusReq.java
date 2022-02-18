package com.example.demo.src.my.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchPetStatusReq {
    private int petIdx;
    private String petStatus;
}
