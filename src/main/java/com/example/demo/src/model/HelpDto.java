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
public class HelpDto {
    private Long idx;
    private String source;
    private String helptitle;
    private String helpurl;

    public HelpDto(Help entity){
        this.idx = entity.getIdx();
        this.source = entity.getSource();
        this.helptitle = entity.getHelptitle();
        this.helpurl = entity.getHelpurl();
    }
}
