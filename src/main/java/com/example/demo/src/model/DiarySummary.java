package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiarySummary {
    private Integer idx;
    private String title;
    private String context;
    private Date date;
    private String imgurl;
    private String day;
    private String dayofweek;

    public DiarySummary(Integer idx, String title, String context, Date date, String imgurl){
        this.idx = idx;
        this.title = title;
        this.context = context;
        this.date = date;
        this.imgurl = imgurl;
        Calendar calendar =  Calendar.getInstance();
        calendar.setTime(date);
        int val = calendar.get(Calendar.DAY_OF_WEEK);
        if (val == 1){ day = "월"; }
        else if (val == 2) { day = "화"; }
        else if (val == 3) { day = "수"; }
        else if (val == 4) { day = "목"; }
        else if (val == 5) { day = "금"; }
        else if (val == 6) { day = "토"; }
        else if (val == 7) { day = "일"; }
    }
}