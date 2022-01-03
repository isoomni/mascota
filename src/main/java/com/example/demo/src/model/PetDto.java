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
public class PetDto {
    private Integer idx;
    private String imgurl;
    private String name;
    private String type;
    private Date birth;

    public PetDto(Pet entity){
        this.idx = entity.getIdx();
        this.imgurl = entity.getImgurl();
        this.name = entity.getName();
        this.type = entity.getType();
        this.birth = entity.getBirth();
    }

    public Pet toEntity(){
        return Pet.builder()
                .petDto(this)
                .build();
    }
}