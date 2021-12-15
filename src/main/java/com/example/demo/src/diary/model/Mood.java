package com.example.demo.src.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.lang.*;

@Getter
@Setter
@AllArgsConstructor
public class Mood {
    private int petIdx;
    private String name;
    private String petType; // 기분

    @Override
    public int hashCode() {
        return (petIdx + name + petType).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Mood) {
            Mood temp = (Mood)obj;
            return petIdx == temp.petIdx && name.equals(temp.name) && petType.equals(temp.petType);
        } else {
            return false;
        }
    }


}
