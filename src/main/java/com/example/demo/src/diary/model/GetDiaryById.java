package com.example.demo.src.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class GetDiaryById {
    private GetDiaryDetail getDiaryDetail;
    private ArrayList<String> imgUrls;
    private HashSet<Mood> moods;
}

