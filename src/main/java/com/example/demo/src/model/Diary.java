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

    @Column(nullable = true)
    private Integer type;

    private Date date;

//    @OneToMany(mappedBy="diary")
//    List<DiaryImg> imgs = new ArrayList<DiaryImg>();

    @Builder
    public Diary(User user, DiaryList diaryList, DiaryDto diaryDto) {
        this.user = user;
        this.diaryList = diaryList;
        this.title = diaryDto.getTitle();
        this.context = diaryDto.getContext();
        this.type = diaryDto.getType();
        this.date = diaryDto.getDate();
    }
}
