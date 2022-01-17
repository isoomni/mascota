package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelpHome{
    private DiarySummary diarySummary;
    private List<HelpDto> helpList;

    public HelpHome(DiarySummary d, List<Help> helpList){
        this.diarySummary = d;
        helpList.forEach(i -> {
            this.helpList.add(new HelpDto(i));
        });
    }
}