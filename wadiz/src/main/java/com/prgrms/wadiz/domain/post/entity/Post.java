package com.prgrms.wadiz.domain.post.entity;

import com.prgrms.wadiz.domain.post.PostStatus;
import com.prgrms.wadiz.domain.seller.entity.Seller;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String thumbNailImage;

    @Lob
    private String content;

    private String image;   // TODO: 게시글에서 여러 사진을 첨부받는 경우 설계를 어떻게 해야하는가

    @Column(nullable = false)
    private int fundingTargetAmount;

    @Column(nullable = false)
    private LocalDateTime fundingStartAt;

    @Column(nullable = false)
    private LocalDateTime fundingEndAt;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;
}
