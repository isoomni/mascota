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
public class DiaryListDto {
    private Integer idx;
    private String context;

    public DiaryListDto(DiaryList entity){
        this.idx = entity.getIdx();
        this.context = entity.getContext();
    }
}