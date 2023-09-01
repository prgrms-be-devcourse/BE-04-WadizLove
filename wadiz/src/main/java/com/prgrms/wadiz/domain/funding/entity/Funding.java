package com.prgrms.wadiz.domain.funding.entity;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "fundings")
@NoArgsConstructor(access = PROTECTED)
public class Funding extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundingId;

    @Column(nullable = false)
    private Integer fundingTargetAmount;

    @Column(nullable = false)
    private LocalDateTime fundingStartAt;

    @Column(nullable = false)
    private LocalDateTime fundingEndAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FundingCategory fundingCategory;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FundingStatus fundingStatus;

    @Builder
    public Funding(
            Integer fundingTargetAmount,
            LocalDateTime fundingStartAt,
            LocalDateTime fundingEndAt,
            FundingCategory fundingCategory,
            FundingStatus fundingStatus
    ) {
        this.fundingTargetAmount = fundingTargetAmount;
        this.fundingStartAt = fundingStartAt;
        this.fundingEndAt = fundingEndAt;
        this.fundingCategory = fundingCategory;
        this.fundingStatus = fundingStatus;
    }
}
