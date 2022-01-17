package com.example.demo.src.home.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetHomeRes {
    private GetHomeNicknameRes getHomeNicknameRes;
    private List<GetHomePetsRes> getHomePetsRes;
    private GetHomeLevelRes getHomeLevelRes;
}
