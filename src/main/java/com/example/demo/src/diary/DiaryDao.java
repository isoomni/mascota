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

    public int checkUser(int idx){
        String Query = "select exists(select nickname from user where idx = ? and status = 'N')";
        return this.jdbcTemplate.queryForObject(Query,
                int.class,
                idx);
    }

    public int checkList(int userIdx, int listIdx){
        String Query = "select exists(select context from diaryList where userIdx = ? and idx = ? and status = 'N')";
        Object[] param = new Object[]{userIdx, listIdx};
        return this.jdbcTemplate.queryForObject(Query,
                int.class,
                param);
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
                "join diaryImg on diaryImg.diaryIdx = diary.idx and diaryImg.status = 'N'\n" +
                "where listIdx = ? and diary.status = 'N'\n" +
                "group by diary.idx\n" +
                "order by diary.date desc diary.idx;";
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
                "join diaryImg on diaryImg.diaryIdx = diary.idx and diaryImg.status = 'N'\n" +
                "join mood on mood.diaryIdx = diary.idx and mood.status = 'N'\n" +
                "join pet on mood.petIdx = pet.idx\n" +
                "where diary.idx = ? and diary.status = 'N' order by mood.petIdx";

        ArrayList<String> imgs = new ArrayList<String>();
        ArrayList<Mood> moods = new ArrayList<Mood>();

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
                        if (!moods.contains(mood)){
                            moods.add(mood);
                        }

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

    public int updateDiary(GetDiaryById getDiaryById){
        GetDiaryDetail input = getDiaryById.getGetDiaryDetail();
        int idx = input.getIdx();
        Object[] Params = new Object[]{input.getTitle(), input.getContext(), input.getType(), input.getDate(),input.getIdx()};
        String Query = "update diary set title = ?, context = ?, type = ?, date = ? where idx = ?";
        if (!getDiaryById.getImgUrls().isEmpty()){
            String imgQuery = "select idx from diaryImg where diaryIdx = ?";
            ArrayList<String> imgurls = getDiaryById.getImgUrls();
            List<String> imgs = this.jdbcTemplate.query(imgQuery,(rs,rowNum)->rs.getString("idx"),idx);
            if (imgs.size() <= imgurls.size()){
                String query = "update diaryImg set imgUrl = ?, status = 'N' where idx = ?";
                for (int i = 0; i < imgs.size(); i++){
                    Object[] Param = new Object[]{imgurls.get(i),imgs.get(i)};
                    this.jdbcTemplate.update(query,Param);
                }
                query = "insert into diaryImg(imgUrl,diaryIdx) values (?,?);";
                for (int i = imgs.size(); i < imgurls.size(); i++){
                    Object[] Param = new Object[]{imgurls.get(i),idx};
                    this.jdbcTemplate.update(query,Param);
                }
            }
            else{
                String query = "update diaryImg set imgUrl = ?, status = 'N' where idx = ?";
                for (int i = 0; i < imgurls.size(); i++){
                    Object[] Param = new Object[]{imgurls.get(i),imgs.get(i)};
                    this.jdbcTemplate.update(query,Param);
                }
                query = "update diaryImg set status = 'D' where idx = ?";
                for (int i = imgurls.size(); i < imgs.size(); i++){
                    this.jdbcTemplate.update(query,imgs.get(i));
                }
            }
        }

        if (!getDiaryById.getMoods().isEmpty()){
            String moodQuery = "select idx from mood where diaryIdx = ?";
            ArrayList<Mood> moods = getDiaryById.getMoods();
            List<String> moodidx = this.jdbcTemplate.query(moodQuery,(rs,rowNum)->rs.getString("idx"),idx);
            if (moodidx.size() <= moods.size()){
                String mquery = "update mood set petIdx = ?, type = ?, status = 'N' where idx = ?";
                for (int i = 0; i < moodidx.size(); i++){
                    Mood tmp = moods.get(i);
                    Object[] Parameter = new Object[]{tmp.getPetIdx(), tmp.getPetType(),moodidx.get(i)};
                    this.jdbcTemplate.update(mquery,Parameter);
                }
                mquery = "insert into mood(petIdx,type,diaryIdx) values (?,?,?);";
                for (int i = moodidx.size(); i < moods.size(); i++){
                    Mood tmp = moods.get(i);
                    Object[] Parameter = new Object[]{tmp.getPetIdx(), tmp.getPetType(),idx};
                    this.jdbcTemplate.update(mquery,Parameter);
                }
            }
            else{
                String mquery = "update mood set petIdx = ?, type = ?, status = 'N' where idx = ?";
                for (int i = 0; i < moods.size(); i++){
                    Mood tmp = moods.get(i);
                    Object[] Parameter = new Object[]{tmp.getPetIdx(),tmp.getPetType(),moodidx.get(i)};
                    this.jdbcTemplate.update(mquery,Parameter);
                }
                mquery = "update mood set status = 'D' where idx = ?";
                for (int i = moods.size(); i < moodidx.size(); i++){
                    this.jdbcTemplate.update(mquery,moodidx.get(i));
                }
            }
        }


        return this.jdbcTemplate.update(Query, Params);
    }

    public GetDiaryDetail chkDiary(int idx){
        String query = "select idx, title, context, type, date from diary where idx = ? and status = 'N'";
        return this.jdbcTemplate.queryForObject(query,
                (rs,rowNum) -> new GetDiaryDetail(
                        rs.getInt("idx"),
                        rs.getString("title"),
                        rs.getString("context"),
                        rs.getString("type"),
                        rs.getString("date"))
                ,idx);
    }

    public int deleteDiary(int idx){
        String query = "update diary set status = 'D' where idx = ?";
        return this.jdbcTemplate.update(query,idx);
    }

    public int insertLists(int userIdx,String context,List<Lists> chk){
        String query = "insert into diaryList(userIdx,context,num) values (?,?,?)";
        Object[] param = new Object[]{userIdx,context,chk.size()};
        String updateQuery = "update diaryList set context = ?, num = ?, status = 'N' where idx = ?";
        boolean flag = false;
        for (int i = 0; i < chk.size(); i++){
            Lists tmp = chk.get(i);
            if (tmp.getStatus().equals("D")){
                Object[] pa = new Object[]{context,i,tmp.getListIdx()};
                return this.jdbcTemplate.update(updateQuery,pa);
            }
        }

        return this.jdbcTemplate.update(query,param);
    }

    public int updateLists(int userIdx, List<String> lists,List<Lists> chk){
        if (lists.size() <= chk.size()){ // 삭제
            String query = "update diaryList set context = ?, num = ?, status = 'N' where idx = ?";
            for (int i = 0; i < lists.size(); i++){
                Lists tmp = chk.get(i);
                Object[] param = new Object[]{lists.get(i),i,tmp.getListIdx()};
                this.jdbcTemplate.update(query,param);
            }
            query = "update diaryList set status = 'D' where idx = ?";
            for (int i = lists.size(); i < chk.size(); i++){
                Lists tmp = chk.get(i);
                this.jdbcTemplate.update(query,tmp.getListIdx());
            }
            return 1;
        }
        else { // 추가
            String query = "update diaryList set context = ?, num = ?, status = 'N' where idx = ?";
            String addquery = "insert into diaryList(userIdx,context,num) values ";
            int len = addquery.length();
            for (int i = 0; i < lists.size(); i++){
                if (i >= chk.size()){
                    addquery += "(" + userIdx + ",'" + lists.get(i) + "'," + i + "),";
                    continue;
                }
                Lists tmp = chk.get(i);
                Object[] param = new Object[]{lists.get(i),i,tmp.getListIdx()};
                this.jdbcTemplate.update(query,param);
            }
            if (len < addquery.length()){
                addquery = addquery.substring(0,addquery.length()-1);
                return this.jdbcTemplate.update(addquery);
            }
            else{
                return 1;
            }
        }
    }

    public List<Lists> getlists(int userIdx){
        int idx = userIdx;
        String Query = "select idx, context, status from diaryList where userIdx = ? order by idx";

        return this.jdbcTemplate.query(Query,
                (rs,rowNum) ->  new Lists(rs.getInt("idx"), rs.getString("context"),rs.getString("status")),idx);
    }

    public int deleteLists(int idx){
        String query = "update diaryList set status = 'D' where idx = ?";
        return this.jdbcTemplate.update(query,idx);
    }

}
