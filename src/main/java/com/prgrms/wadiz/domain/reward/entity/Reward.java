package com.prgrms.wadiz.domain.reward.entity;

import com.prgrms.wadiz.domain.reward.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "rewards")
@NoArgsConstructor(access = PROTECTED)
public class Reward extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reward_id")
    private Long rewardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id")
    private Funding funding;

    @Column(nullable = false)
    private String rewardName;

    @Column(nullable = false)
    private Integer rewardQuantity; // 수정 가능

    @Column(nullable = false)
    private Integer rewardPrice;

    @Column(nullable = false)
    private RewardType rewardType;

    @Enumerated(EnumType.STRING)
    private RewardStatus rewardStatus; // 수정 가능


}
