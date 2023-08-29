package com.prgrms.wadiz.domain.funding.entity;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.seller.entity.Seller;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "fundings")
@NoArgsConstructor(access = PROTECTED)
public class Funding extends BaseEntity {

    @Id
    @Column(name="funding_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private Integer fundingTargetAmount;

    @Column(nullable = false)
    private LocalDateTime fundingStartAt;

    @Column(nullable = false)
    private LocalDateTime fundingEndAt;

    @Column(nullable = false)
    private FundingCategory fundingCategory;

    @Enumerated(EnumType.STRING)
    private FundingStatus fundingStatus;


}
