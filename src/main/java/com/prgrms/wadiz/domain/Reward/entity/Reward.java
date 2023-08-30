package com.prgrms.wadiz.domain.Reward.entity;

import com.prgrms.wadiz.domain.Reward.RewardType;
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
    private Integer rewardQuantity;

    @Column(nullable = false)
    private Integer rewardPrice;

    @Column(nullable = false)
    private RewardType rewardType;


}
