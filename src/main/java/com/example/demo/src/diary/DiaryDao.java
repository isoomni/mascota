package com.example.demo.src.diary;

import com.example.demo.src.diary.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.lang.Object;


@Repository
public class DiaryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetDiaryRes> getDiarylist(int listIdx){
        int idx = listIdx;
        String Query = "select diary.idx, diaryList.context as list, diary.title,diary.context, diary.type,diary.date,\n" +
                "case dayofweek(diary.createdAt) \n" +
                "when '1' then '일요일'\n" +
                "when '2' then '월요일'\n" +
                "when '3' then '화요일'\n" +
                "when '4' then '수요일'\n" +
                "when '5' then '목요일'\n" +
                "when '6' then '금요일'\n" +
                "when '7' then '토요일'\n" +
                "end as day, diaryImg.imgUrl from diary\n" +
                "join diaryList on diaryList.idx = diary.listIdx\n" +
                "join diaryImg on diaryImg.diaryIdx = diary.idx \n" +
                "where listIdx = ? and diary.status = 'N'\n" +
                "group by diary.idx\n" +
                "order by diary.createdAt desc;";
        return this.jdbcTemplate.query(Query,
                (rs,rowNum) -> new GetDiaryRes(
                        rs.getInt("idx"),
                        rs.getString("list"),
                        rs.getString("title"),
                        rs.getString("context"),
                        rs.getString("type"),
                        rs.getString("date"),
                        rs.getString("day"),
                        rs.getString("imgUrl")),
                idx);
    }

    public GetDiaryById getDiaryDetail(int diaryIdx) {
        int idx = diaryIdx;
        String Query = "select diary.idx, title, context, diary.type, diary.date, diaryImg.imgUrl, mood.petIdx, pet.name, mood.type as petType\n" +
                "from diary \n" +
                "join diaryImg on diaryImg.diaryIdx = diary.idx\n" +
                "join mood on mood.diaryIdx = diary.idx\n" +
                "join pet on mood.petIdx = pet.idx\n" +
                "where diary.idx = ?";

        ArrayList<String> imgs = new ArrayList<String>();
        HashSet<Mood> moods = new HashSet<Mood>();

        List<GetDiaryDetail> ans = this.jdbcTemplate.query(Query,
                new RowMapper<GetDiaryDetail>() {
                    @Override
                    public GetDiaryDetail mapRow (ResultSet rs,int rowNum) throws SQLException{
                        GetDiaryDetail result = new GetDiaryDetail(rs.getInt("idx"),rs.getString("title"), rs.getString("context"), rs.getString("type"), rs.getString("date"));
                        String chk = rs.getString("imgUrl");
                        if (!imgs.contains(chk)){
                            imgs.add(chk);
                        }
                        Mood mood = new Mood(rs.getInt("petIdx"), rs.getString("name"), rs.getString("petType"));
                        moods.add(mood);

                        return result;
                    }
                }, idx);

        if (ans.isEmpty()){
            return null;
        }

        return new GetDiaryById(ans.get(0),imgs,moods);
    }

    public List<Lists> getDiarylists(int userIdx){
        int idx = userIdx;
        String Query = "select idx, context from diaryList where userIdx = ? and status = 'N' order by num";

        return this.jdbcTemplate.query(Query,
                (rs,rowNum) ->  new Lists(rs.getInt("idx"), rs.getString("context")),idx);
    }

    public GetDiaryDetail postDiary(PostDiaryReq postDiaryReq){
        String createUserQuery = "insert into diary(userIdx, listIdx, title, context, type, date) values (?,?,?,?,?,?)";
        String lastInserIdQuery = "select last_insert_id()";
        String query = "insert into diaryImg(diaryIdx,imgUrl) values ";
        String Moodquery = "insert into mood(diaryIdx,petIdx,type) values ";
        String chk = "select idx, title, context, type, date from diary where idx = ?";
        Object[] createUserParams = new Object[]{postDiaryReq.getUserIdx(), postDiaryReq.getListIdx(),
                postDiaryReq.getTitle(), postDiaryReq.getContext(), postDiaryReq.getType(), postDiaryReq.getDate()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);
        String lastInsertId = this.jdbcTemplate.queryForObject(lastInserIdQuery,String.class);
        ArrayList<String> val = postDiaryReq.getImgUrls();
        ArrayList<Mood> m = postDiaryReq.getMoods();
        for (String imgUrl : val){
            query += "('" + lastInsertId + "','" + imgUrl + "'),";
        }
        for (Mood mood : m){
            Moodquery += "('" + lastInsertId + "','" + mood.getPetIdx() + "','" + mood.getPetType() + "'),";
        }
        int len = query.length();
        query = query.substring(0,len-1);
        len = Moodquery.length();
        Moodquery = Moodquery.substring(0,len-1);
        this.jdbcTemplate.update(query);
        this.jdbcTemplate.update(Moodquery);
        return this.jdbcTemplate.queryForObject(chk,
                (rs,rowNum) -> new GetDiaryDetail(
                        rs.getInt("idx"),
                        rs.getString("title"),
                        rs.getString("context"),
                        rs.getString("type"),
                        rs.getString("date")),
                lastInsertId);
    }
}
