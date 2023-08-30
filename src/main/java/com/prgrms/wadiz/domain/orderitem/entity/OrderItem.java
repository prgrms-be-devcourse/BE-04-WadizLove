package com.prgrms.wadiz.domain.orderitem.entity;

import com.prgrms.wadiz.domain.Reward.entity.Reward;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id")
    private Reward reward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private Integer orderItemPrice;

    @Column(nullable = false)
    private Integer orderItemQuantity;
}
