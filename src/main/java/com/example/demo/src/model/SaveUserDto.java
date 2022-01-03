package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@NoArgsConstructor
@Getter
@Setter
public class SaveUserDto{
    private String id;
    private String password;
    private String updatepassword;

    @Builder
    public SaveUserDto(String id, String password){
        this.id = id;
        this.password = password;
    }

    public User toEntity(){
            return User.builder()
            .id(id)
            .password(password)
            .build();
    }

}