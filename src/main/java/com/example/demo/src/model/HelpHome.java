package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelpHome{
    private DiarySummary diarySummary;
    private List<HelpDto> helpList = new ArrayList<HelpDto>();

    public HelpHome(DiarySummary d, List<Help> h){
        this.diarySummary = d;
        h.forEach(i -> {
            this.helpList.add(new HelpDto(i));
        });
    }
}