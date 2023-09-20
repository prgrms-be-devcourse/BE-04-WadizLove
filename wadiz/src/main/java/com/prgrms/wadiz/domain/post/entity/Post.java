package com.prgrms.wadiz.domain.post.entity;

import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    @NotBlank(message = "제목은 비워둘 수 없습니다.")
    private String postTitle;

    @Lob
    @Column(nullable = false)
    @NotBlank(message = "상세 설명은 비워둘 수 없습니다.")
    private String postDescription;

    @Column(nullable = false)
    @Pattern(
            regexp = "^https?://.*\\.(?:png|jpg|jpeg|gif)$",
            message = "올바른 이미지 URL 형식이 아닙니다."
    )
    @NotBlank(message = "게시물 섬네일 이미지를 입력해주세요.")
    private String postThumbNailImage;

    @Column(nullable = false)
    @Pattern(
            regexp = "^https?://.*\\.(?:png|jpg|jpeg|gif)$",
            message = "올바른 이미지 URL 형식이 아닙니다."
    )
    @NotBlank(message = "게시물 상세 이미지를 입력해주세요.")
    private String postContentImage;

    @Builder
    public Post(
            Long postId,
            Project project,
            String postTitle,
            String postDescription,
            String postThumbNailImage,
            String postContentImage
    ) {
        this.postId = postId;
        this.project = project;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postThumbNailImage = postThumbNailImage;
        this.postContentImage = postContentImage;
    }

    public void updatePost(
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
