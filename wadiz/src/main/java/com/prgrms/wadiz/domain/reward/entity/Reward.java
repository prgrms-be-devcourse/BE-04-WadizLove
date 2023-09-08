package com.prgrms.wadiz.domain.reward.entity;

import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.domain.BaseEntity;

import com.prgrms.wadiz.global.annotation.ValidEnum;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


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
    @NotBlank(message = "리워드명을 입력해주세요.")
    private String rewardName;

    @Lob
    @Column(nullable = false)
    @NotBlank(message = "리워드 설명을 입력해주세요.")
    private String rewardDescription;

    @Column(nullable = false)
    @Min(value = 1, message = "리워드 재고는 최소 1개 이상입니다.")
    private Integer rewardQuantity;

    @Column(nullable = false)
    @Min(value = 10, message = "리워드 가격은 10원 이상이어야 합니다.")
    private Integer rewardPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = RewardType.class, message = "해당하는 리워드 타입이 존재하지 않습니다.")
    private RewardType rewardType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = RewardStatus.class, message = "해당하는 리워드 상태가 존재하지 않습니다.")
    private RewardStatus rewardStatus;

    @Column(nullable = false)
    private Boolean activated = Boolean.TRUE; // 활성화 여부, 삭제 시 -> false

    public void removeStock(Integer rewardQuantity) {
        int restQuantity = this.rewardQuantity - rewardQuantity;

        if (restQuantity < ZERO_STOCK) {
            throw new BaseException(ErrorCode.UNKNOWN);
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
            RewardStatus rewardStatus) {
        this.rewardName = rewardName;
        this.rewardDescription = rewardDescription;
        this.rewardQuantity = rewardQuantity;
        this.rewardPrice = rewardPrice;
        this.rewardType = rewardType;
        this.rewardStatus = rewardStatus;
    }

    public void deletedStatus() {
        activated = Boolean.FALSE;
    }


    public void allocateProject(Project project) {
        this.project = project;
    }

    public void addQuantity(Integer orderRewardQuantity) {
        this.rewardQuantity += orderRewardQuantity;
    }
}




