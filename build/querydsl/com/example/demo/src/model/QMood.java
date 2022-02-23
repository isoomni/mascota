package com.example.demo.src.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMood is a Querydsl query type for Mood
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMood extends EntityPathBase<Mood> {

    private static final long serialVersionUID = -76908800L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMood mood = new QMood("mood");

    public final QDiary diary;

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath type = createString("type");

    public QMood(String variable) {
        this(Mood.class, forVariable(variable), INITS);
    }

    public QMood(Path<? extends Mood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMood(PathMetadata metadata, PathInits inits) {
        this(Mood.class, metadata, inits);
    }

    public QMood(Class<? extends Mood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.diary = inits.isInitialized("diary") ? new QDiary(forProperty("diary"), inits.get("diary")) : null;
    }

}

