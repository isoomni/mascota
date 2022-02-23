package com.example.demo.src.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QHelp is a Querydsl query type for Help
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHelp extends EntityPathBase<Help> {

    private static final long serialVersionUID = -77067446L;

    public static final QHelp help = new QHelp("help");

    public final StringPath helptitle = createString("helptitle");

    public final StringPath helpurl = createString("helpurl");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath source = createString("source");

    public QHelp(String variable) {
        super(Help.class, forVariable(variable));
    }

    public QHelp(Path<? extends Help> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHelp(PathMetadata metadata) {
        super(Help.class, metadata);
    }

}

