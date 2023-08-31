package com.prgrms.wadiz.domain.orderReward.entity;

import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_rewards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderReward extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderRewardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id")
    private Reward reward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private Integer orderRewardPrice;

    @Column(nullable = false)
    private Integer orderRewardQuantity;
}
