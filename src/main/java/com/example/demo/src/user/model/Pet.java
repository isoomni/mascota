package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pet {
    private int idx;
    private String name;
    private String imgUrl;
    private String type;
    private String birth;
}
