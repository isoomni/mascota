package com.example.demo.src.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class GetDiaryDetail {
    private int idx;
    private String title;
    private String context;
    private String type;
    private String date;
}

