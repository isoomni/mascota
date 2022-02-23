package com.example.demo.src.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiaryImg is a Querydsl query type for DiaryImg
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDiaryImg extends EntityPathBase<DiaryImg> {

    private static final long serialVersionUID = -937389943L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiaryImg diaryImg = new QDiaryImg("diaryImg");

    public final QDiary diary;

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final StringPath imgurl = createString("imgurl");

    public QDiaryImg(String variable) {
        this(DiaryImg.class, forVariable(variable), INITS);
    }

    public QDiaryImg(Path<? extends DiaryImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiaryImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiaryImg(PathMetadata metadata, PathInits inits) {
        this(DiaryImg.class, metadata, inits);
    }

    public QDiaryImg(Class<? extends DiaryImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.diary = inits.isInitialized("diary") ? new QDiary(forProperty("diary"), inits.get("diary")) : null;
    }

}

