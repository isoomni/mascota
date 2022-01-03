package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class DiaryDto {
    private Integer idx;
    private String title;
    private String context;
    private Integer type;
    private Date date;

    public DiaryDto(Diary entity){
        this.idx = entity.getIdx();
        this.title = entity.getTitle();
        this.context = entity.getContext();
        this.type = entity.getType();
        this.date = entity.getDate();
    }

    public Diary toEntity(){
        return Diary.builder()
                .diaryDto(this)
                .build();
    }
}