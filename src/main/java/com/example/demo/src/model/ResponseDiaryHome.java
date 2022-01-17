package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.lang.String;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class ResponseDiaryHome {
    private String listtitle;

    TreeMap<String,ArrayList<DiarySummary>> records = new TreeMap<String,ArrayList<DiarySummary>>(Collections.reverseOrder());

    @Builder
    public ResponseDiaryHome(String listtitle, List<DiarySummary> summarys){
        this.listtitle = listtitle;
        summarys.forEach(d -> {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String str = dateFormat.format(d.getDate());
            String key = str.substring(0,7);
            d.setDayofweek(str.substring(8));
            d.setDate(null);
            ArrayList<DiarySummary> now = new ArrayList<>();
            if (this.records.containsKey(key)){
                now = records.get(key);
                records.get(key).add(d);
            }
            else{
                now.add(d);
            }
            this.records.put(key,now);
        });
    }
}