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
public class UserDto {
    private Integer idx;
    private String id;
    private String nickname;
    private String imgurl;
    private String title;
    private String jwt;
    private Integer user_id;

    Set<PetDto> pets = new HashSet<>();

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