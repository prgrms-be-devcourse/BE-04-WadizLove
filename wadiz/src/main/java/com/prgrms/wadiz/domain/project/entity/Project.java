package com.prgrms.wadiz.domain.project.entity;

import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "projects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @OneToOne
    @JoinColumn(name = "maker_id")
    private Maker maker;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "funding_id")
    private Funding funding;

    @Builder
    public Project(
            Maker maker,
            Post post,
            Funding funding
    ) {
        this.maker = maker;
        this.post = post;
        this.funding = funding;
    }
}
