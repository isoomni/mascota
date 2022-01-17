package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class DiaryImgDto implements Comparable<DiaryImgDto>{
    private Integer idx;
    private String imgurl;
    
    @Override
    public int compareTo(DiaryImgDto d){
        if (this.idx > d.idx){
            return 1;
        }
        else if (this.idx < d.idx){
            return -1;
        }
        else{
            return 0;
        }
    }

    public DiaryImgDto(DiaryImg entity){
        this.idx = entity.getIdx();
        this.imgurl = entity.getImgurl();
    }
}