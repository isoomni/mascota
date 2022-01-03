package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.util.List;
import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Setter
public class UpdateDiaryListsDto{
    private List<String> lists = new ArrayList<>();

}