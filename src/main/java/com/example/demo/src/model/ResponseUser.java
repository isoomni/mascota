package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class ResponseUser {
    private Integer idx;
    private String jwt;

    public ResponseUser(User entity){
        this.idx = entity.getIdx();
        this.jwt = entity.getJwt();
    }
}