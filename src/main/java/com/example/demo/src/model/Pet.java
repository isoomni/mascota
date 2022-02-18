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
@Entity(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 2000, nullable = false)
    private String imgurl;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 1, nullable = false)
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birth;

    private String status = "N";

    @Builder
    public Pet(User user, PetDto petDto) {
        this.user = user;
        this.imgurl = petDto.getImgurl();
        this.name = petDto.getName();
        this.type = petDto.getType();
        this.birth = petDto.getBirth();
    }
}
