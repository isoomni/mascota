<<<<<<< HEAD
package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "help")
public class Help {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 45, nullable = false)
    private String source;

    @Column(length = 45, nullable = false)
    private String helptitle;

    @Column(length = 2000, nullable = false)
    private String helpurl;

}
=======
package com.example.demo.src.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "help")
public class Help {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 45, nullable = false)
    private String source;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String helpurl;

}
>>>>>>> fe947a315c6d9c4aeffaccd472c120edea4514ad
