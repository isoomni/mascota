package com.example.demo.src.my.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMyPageRes {
    private GetMyUser getMyUser;
    private GetMyBook getMyBook;
    private List<GetMyPets> getMyPets;
}
