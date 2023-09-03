package com.prgrms.wadiz.domain.funding.entity;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "fundings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
            FundingCategory fundingCategory
            //FundingStatus fundingStatus
    ) {
        this.fundingTargetAmount = fundingTargetAmount;
        this.fundingStartAt = fundingStartAt;
        this.fundingEndAt = fundingEndAt;
        this.fundingCategory = fundingCategory;
        this.fundingStatus = FundingStatus.OPEN;    // 생성 시점에서 상태를 고정하는 방식(서비스 layer를 신경쓰지 않아도 됨!)
    }

    // funding에 대한 상태 변경을 의미하는 메소드를 도메인에서 정의하고, 필요한 시점에 서비스에서 호출하는 방식으로 진행
}
