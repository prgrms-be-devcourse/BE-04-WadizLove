package com.prgrms.wadiz.domain.post.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String thumbNailImage;

    @Column(nullable = false)
    private String contentImage;

}
