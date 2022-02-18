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
public class UserDto {
    private Integer idx;
    private String id;
    private String nickname;
    private String imgurl;
    private String title;
    private String jwt;

    List<PetDto> pets = new ArrayList<>();

    public UserDto(User entity){
        this.idx = entity.getIdx();
        this.id = entity.getId();
        this.nickname = entity.getNickname();
        this.imgurl = entity.getImgurl();
        this.title = entity.getTitle();
        this.jwt = entity.getJwt();
        entity.getPets().forEach(p -> {
            this.pets.add(new PetDto(p));
        });
    }
}