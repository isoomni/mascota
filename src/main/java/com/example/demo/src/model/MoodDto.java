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
public class MoodDto implements Comparable<MoodDto>{
    private Integer idx;
    private String name;
    private String type;

    @Override
    public int compareTo(MoodDto m){
        if (this.idx > m.idx){
            return 1;
        }
        else if (this.idx < m.idx){
            return -1;
        }
        else{
            return 0;
        }
    }

    public MoodDto(Mood entity){
        this.idx = entity.getIdx();
        this.name = entity.getName();
        this.type = entity.getType();
    }
}