package com.example.demo.src.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class PostDiaryReq {
    private int userIdx;
    private int listIdx;
    private String title;
    private String context;
    private String type;
    private String date;
    private ArrayList<String> imgUrls;
    private ArrayList<Mood> moods;
}

