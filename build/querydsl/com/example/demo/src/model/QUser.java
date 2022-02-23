package com.example.demo.src.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -76666924L;

    public static final QUser user = new QUser("user");

    public final StringPath id = createString("id");

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final StringPath imgurl = createString("imgurl");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<Pet, QPet> pets = this.<Pet, QPet>createList("pets", Pet.class, QPet.class, PathInits.DIRECT2);

    public final StringPath status = createString("status");

    public final StringPath title = createString("title");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

