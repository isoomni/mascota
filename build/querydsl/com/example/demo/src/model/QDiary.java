package com.example.demo.src.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiary is a Querydsl query type for Diary
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDiary extends EntityPathBase<Diary> {

    private static final long serialVersionUID = 1902291162L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiary diary = new QDiary("diary");

    public final StringPath context = createString("context");

    public final DatePath<java.util.Date> date = createDate("date", java.util.Date.class);

    public final QDiaryList diaryList;

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final SetPath<DiaryImg, QDiaryImg> imgurls = this.<DiaryImg, QDiaryImg>createSet("imgurls", DiaryImg.class, QDiaryImg.class, PathInits.DIRECT2);

    public final SetPath<Mood, QMood> moods = this.<Mood, QMood>createSet("moods", Mood.class, QMood.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final QUser user;

    public QDiary(String variable) {
        this(Diary.class, forVariable(variable), INITS);
    }

    public QDiary(Path<? extends Diary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiary(PathMetadata metadata, PathInits inits) {
        this(Diary.class, metadata, inits);
    }

    public QDiary(Class<? extends Diary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.diaryList = inits.isInitialized("diaryList") ? new QDiaryList(forProperty("diaryList"), inits.get("diaryList")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

