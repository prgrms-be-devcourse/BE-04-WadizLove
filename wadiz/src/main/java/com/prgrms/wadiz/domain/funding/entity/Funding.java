package com.prgrms.wadiz.domain.funding.entity;

import com.prgrms.wadiz.domain.BaseEntity;
import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.project.entity.Project;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "fundings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Funding extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundingId;

    @OneToOne( //TODO: 적용해줘야 하나 고민 중 + cascade
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    @Min(
            value = 1,
            message = "펀딩 모집 금액은 양수만 허용됩니다."
    )
    private Integer fundingTargetAmount;

    @Column(nullable = false)
    private LocalDateTime fundingStartAt;

    @Column(nullable = false)
    private LocalDateTime fundingEndAt;

    @Column(nullable = false)
    private Integer fundingParticipants;

    @Column(nullable = false)
    private Integer fundingAmount;

    @Column(nullable = false)
    private Boolean fundingSuccess = Boolean.FALSE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FundingCategory fundingCategory;

    @Builder
    public Funding(
            Project project,
            Integer fundingTargetAmount,
            LocalDateTime fundingStartAt,
            LocalDateTime fundingEndAt,
            FundingCategory fundingCategory
    ) {
        this.project = project;
        this.fundingTargetAmount = fundingTargetAmount;
        this.fundingStartAt = fundingStartAt;
        this.fundingEndAt = fundingEndAt;
        this.fundingParticipants = 0;
        this.fundingAmount = 0;
        this.fundingCategory = fundingCategory;
    }

    public void updateFunding(
            Integer fundingTargetAmount,
            LocalDateTime fundingStartAt,
            LocalDateTime fundingEndAt,
            FundingCategory fundingCategory
    ) {
        this.fundingTargetAmount = fundingTargetAmount;
        this.fundingStartAt = fundingStartAt;
        this.fundingEndAt = fundingEndAt;
        this.fundingCategory = fundingCategory;
    }

    public void addOrderInfo(Integer totalOrderPrice) {
        this.fundingAmount += totalOrderPrice;
        this.fundingParticipants += 1;
        validateFundingSuccess(
                this.fundingAmount,
                this.fundingTargetAmount
        );
    }

    public void removeOrderInfo(Integer totalOrderPrice) {
        this.fundingAmount -= totalOrderPrice;
        this.fundingParticipants -= 1;
        validateFundingSuccess(
                this.fundingAmount,
                this.fundingTargetAmount
        );
    }

    public static Integer calculateSuccessRate(
            Integer fundingAmount,
            Integer fundingTargetAmount
    ) {
        return Math.round((fundingAmount / fundingTargetAmount) * 100);
    }

    private void validateFundingSuccess(
            Integer fundingAmount,
            Integer fundingTargetAmount
    ) {
        this.fundingSuccess = (fundingAmount >= fundingTargetAmount) ?
                Boolean.TRUE : Boolean.FALSE;
    }
}
