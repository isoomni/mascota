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
@Entity(name = "diarylist")
public class DiaryList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 2000, nullable = false)
    private String context;

    @Column(nullable = true)
    private Integer num;

    private String status = "N";

    @Builder
    public DiaryList(User user, DiaryListDto diaryListDto, Integer num) {
        this.user = user;
        this.context = diaryListDto.getContext();
        this.num = num;
    }
}
