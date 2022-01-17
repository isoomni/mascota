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
@Entity(name = "diaryimg")
public class DiaryImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Column(length = 2000, nullable = false)
    private String imgurl;

    @Builder
    public DiaryImg(Diary diary, String imgurl) {
        this.diary = diary;
        this.imgurl = imgurl;
    }

    @Builder
    public DiaryImg(Diary diary, DiaryImgDto diaryImgDto) {
        this.diary = diary;
        this.idx = diaryImgDto.getIdx();
        this.imgurl = diaryImgDto.getImgurl();
    }
}
