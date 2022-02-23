package com.example.demo.src.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiaryList is a Querydsl query type for DiaryList
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDiaryList extends EntityPathBase<DiaryList> {

    private static final long serialVersionUID = 1005768856L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiaryList diaryList = new QDiaryList("diaryList");

    public final StringPath context = createString("context");

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final NumberPath<Integer> num = createNumber("num", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final QUser user;

    public QDiaryList(String variable) {
        this(DiaryList.class, forVariable(variable), INITS);
    }

    public QDiaryList(Path<? extends DiaryList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiaryList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiaryList(PathMetadata metadata, PathInits inits) {
        this(DiaryList.class, metadata, inits);
    }

    public QDiaryList(Class<? extends DiaryList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

