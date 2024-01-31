package com.prgrms.wadiz.domain.reward.entity;

import com.prgrms.wadiz.domain.BaseEntity;
import com.prgrms.wadiz.domain.project.Project;
import com.prgrms.wadiz.domain.reward.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Slf4j
@Entity
@Getter
@Table(name = "rewards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reward extends BaseEntity {
    private static final int ZERO_STOCK = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rewardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    @NotNull(message = "리워드명을 입력해주세요.")
    private String rewardName;

    @Lob
    @Column(nullable = false)
    @NotNull(message = "리워드 설명을 입력해주세요.")
    private String rewardDescription;

    @Column(nullable = false)
    @Min(value = 0, message = "리워드 재고는 양수입니다.")
    @NotNull(message = "리워드 수량이 필요합니다.")
    private Integer rewardQuantity;

    @Column(nullable = false)
    @Min(value = 10, message = "리워드 가격은 10 이상입니다.")
    @NotNull(message = "리워드 가격이 필요합니다.")
    private Integer rewardPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "리워드 타입을 입력해주세요")
    private RewardType rewardType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "리워드 상태를 입력해주세요")
    private RewardStatus rewardStatus;

    @Column(nullable = false)
    private Boolean activated = Boolean.TRUE;

    public void removeStock(Integer rewardQuantity) {
        int restQuantity = this.rewardQuantity - rewardQuantity;

        if (restQuantity < ZERO_STOCK) {
            log.warn("orderStock is over than rewardQuantity");

            throw new BaseException(ErrorCode.ORDER_COUNT_EXCEED);
        }

        this.rewardQuantity = restQuantity;
    }

    @Builder
    public Reward(
                  Project project,
                  String rewardName,
                  String rewardDescription,
                  Integer rewardQuantity,
                  Integer rewardPrice,
                  RewardType rewardType
                  )
    {   this.project = project;
        this.rewardName = rewardName;
        this.rewardDescription = rewardDescription;
        this.rewardQuantity = rewardQuantity;
        this.rewardPrice = rewardPrice;
        this.rewardType = rewardType;
        this.rewardStatus = RewardStatus.IN_STOCK;
    }

    public void updateReward(
            String rewardName,
            String rewardDescription,
            Integer rewardQuantity,
            Integer rewardPrice,
            RewardType rewardType,
            RewardStatus rewardStatus
    ) {
        this.rewardName = rewardName;
        this.rewardDescription = rewardDescription;
        this.rewardQuantity = rewardQuantity;
        this.rewardPrice = rewardPrice;
        this.rewardType = rewardType;
        this.rewardStatus = rewardStatus;
    }

    public void unActivateStatus() {
        activated = Boolean.FALSE;
    }

    public void addQuantity(Integer orderRewardQuantity) {
        this.rewardQuantity += orderRewardQuantity;
    }
}




