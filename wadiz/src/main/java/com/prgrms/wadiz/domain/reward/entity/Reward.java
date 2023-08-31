package com.prgrms.wadiz.domain.reward.entity;

import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "items")
@NoArgsConstructor(access = PROTECTED)
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

    @Builder
    public Reward(Long rewardId, Integer rewardQuantity, Integer rewardPrice) {
        this.rewardId = rewardId;
        this.rewardQuantity = rewardQuantity;
        this.rewardPrice = rewardPrice;
    }

    public void removeStock(Integer rewardQuantity){
        int restQuantity = this.rewardQuantity - rewardQuantity;

        if (restQuantity < ZERO_STOCK){
            throw new IllegalArgumentException("not good");
        }

        this.rewardQuantity = restQuantity;
    }
}
