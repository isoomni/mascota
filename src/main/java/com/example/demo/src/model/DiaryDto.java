package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class DiaryDto {
    private Integer idx;
    private String title;
    private String context;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    List<String> imgurls = new ArrayList<>();

    List<MoodDto> moods = new ArrayList<>();

    public DiaryDto(Diary entity){
        this.idx = entity.getIdx();
        this.title = entity.getTitle();
        this.context = entity.getContext();
        this.date = entity.getDate();
    }

    public Diary toEntity(){
        return Diary.builder()
                .diaryDto(this)
                .build();
    }
}