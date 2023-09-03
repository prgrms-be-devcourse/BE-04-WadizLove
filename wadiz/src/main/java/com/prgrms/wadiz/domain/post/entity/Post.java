package com.prgrms.wadiz.domain.post.entity;

import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String postTitle;

    @Lob
    @Column(nullable = false)
    private String postDescription;

    @Column(nullable = false)
    private String postThumbNailImage;

    @Column(nullable = false)
    private String postContentImage;

    @Builder
    public Post(
            String postTitle,
            String postDescription,
            String postThumbNailImage,
            String postContentImage
    ) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postThumbNailImage = postThumbNailImage;
        this.postContentImage = postContentImage;
    }
}
