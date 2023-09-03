package com.prgrms.wadiz.domain.reward.entity;

import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.global.BaseEntity;

import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;


@Entity
@Getter
@Table(name = "rewards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE rewards SET activated = false WHERE reward_id = ?")
public class Reward extends BaseEntity {
    private static final int ZERO_STOCK = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rewardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    private String rewardName;

    @Lob
    @Column(nullable = false)
    private String rewardDescription;

    @Column(nullable = false)
    private Integer rewardQuantity;

    @Column(nullable = false)
    private Integer rewardPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardType rewardType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardStatus rewardStatus;

    public void removeStock(Integer rewardQuantity) {
        int restQuantity = this.rewardQuantity - rewardQuantity;

        if (restQuantity < ZERO_STOCK) {
            throw new BaseException(ErrorCode.UNKNOWN);
        }

        this.rewardQuantity = restQuantity;
    }

    @Column(nullable = false)
    private Boolean activated = Boolean.TRUE; // 활성화 여부, 삭제 시 -> false

    @Builder
    public Reward(
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

    public void modifyRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public void modifyRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
    }

    public void modifyRewardQuantity(Integer rewardQuantity) {
        this.rewardQuantity = rewardQuantity;
    }

    public void modifyRewardPrice(Integer rewardPrice) {
        this.rewardPrice = rewardPrice;
    }

    public void modifyRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public void modifyRewardStatus(RewardStatus rewardStatus) {
        this.rewardStatus = rewardStatus;
    }

    public void allocateProject(Project project) {
        this.project = project;
    }

    public static RewardResponseDTO toDTOForResponse(Reward reward) {
        return new RewardResponseDTO(
                reward.getRewardName(),
                reward.getRewardDescription(),
                reward.getRewardQuantity(),
                reward.getRewardPrice(),
                reward.getRewardType(),
                reward.getRewardStatus());
    }

    public void addQuantity(Integer orderRewardQuantity) {
        this.rewardQuantity += orderRewardQuantity;
    }
}




