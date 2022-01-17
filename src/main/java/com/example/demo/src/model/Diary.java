package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private DiaryList diaryList;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String context;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy="diary", fetch = FetchType.LAZY)
    Set<DiaryImg> imgurls = new HashSet<>();

    @OneToMany(mappedBy="diary", fetch = FetchType.LAZY)
    Set<Mood> moods = new HashSet<>();

    public void setByDiaryDto(DiaryDto diaryDto){
        if (diaryDto.getTitle() != null){
            this.title = diaryDto.getTitle();
        }
        if (diaryDto.getContext() != null){
            this.context = diaryDto.getContext();
        }
        if (diaryDto.getDate() != null){
            this.date = diaryDto.getDate();
        }
    }

    public void addImgs(DiaryImg img){
        this.imgurls.add(img);
        img.setDiary(this);
    }

    public void addMoods(Mood mood){
        this.moods.add(mood);
        mood.setDiary(this);
    }

    @Builder
    public Diary(User user, DiaryList diaryList, DiaryDto diaryDto) {
        this.user = user;
        this.diaryList = diaryList;
        this.title = diaryDto.getTitle();
        this.context = diaryDto.getContext();
        this.date = diaryDto.getDate();
    }

}
