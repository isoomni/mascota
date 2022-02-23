package com.example.demo.src.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTest is a Querydsl query type for Test
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTest extends EntityPathBase<Test> {

    private static final long serialVersionUID = -76709733L;

    public static final QTest test = new QTest("test");

    public final StringPath context = createString("context");

    public final DatePath<java.util.Date> date = createDate("date", java.util.Date.class);

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final StringPath title = createString("title");

    public QTest(String variable) {
        super(Test.class, forVariable(variable));
    }

    public QTest(Path<? extends Test> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTest(PathMetadata metadata) {
        super(Test.class, metadata);
    }

}

