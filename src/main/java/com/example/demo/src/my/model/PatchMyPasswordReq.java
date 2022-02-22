package com.example.demo.src.my.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchMyPasswordReq {
    private int userIdx;
    private String oldPassword;
    private String newPassword;
    private String ReNewPassword;
}
