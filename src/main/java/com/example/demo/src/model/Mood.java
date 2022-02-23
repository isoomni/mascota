package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "mood")
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Column
    private String name;

    @Column(length = 20, nullable = false)
    private String type;

    @Builder
    public Mood(Diary diary, MoodDto moodDto) {
        this.diary = diary;
        this.idx = moodDto.getIdx();
        this.name = moodDto.getName();
        this.type = moodDto.getType();
    }

    @Builder
    public Mood(Diary diary, String name) {
        this.diary = diary;
        this.name = name;
    }

}
