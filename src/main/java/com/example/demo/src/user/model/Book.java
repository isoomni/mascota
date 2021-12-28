package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Book {
    private String nickname;
    private String imgUrl;
    private String title;
    private ArrayList<Pet> petList;
}
