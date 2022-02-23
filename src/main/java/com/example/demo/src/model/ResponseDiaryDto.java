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
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class ResponseDiaryDto {
    private Integer idx;
    private String title;
    private String context;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    List<DiaryImgDto> imgurls = new ArrayList<>();

    List<MoodDto> moods = new ArrayList<>();

    public ResponseDiaryDto(Diary entity){
        this.idx = entity.getIdx();
        this.title = entity.getTitle();
        this.context = entity.getContext();
        this.date = entity.getDate();
        entity.getImgurls().forEach(i -> {
           this.imgurls.add(new DiaryImgDto(i));
        });
        entity.getMoods().forEach(m -> {
            this.moods.add(new MoodDto(m));
        });
        Collections.sort(this.imgurls);
        Collections.sort(this.moods);
    }

}