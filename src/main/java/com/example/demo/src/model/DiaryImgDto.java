package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import java.util.HashSet;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class DiaryImgDto {
    private String imgurl;
    
    public DiaryImgDto(DiaryImg entity){
        this.imgurl = entity.getImgurl();
    }
}