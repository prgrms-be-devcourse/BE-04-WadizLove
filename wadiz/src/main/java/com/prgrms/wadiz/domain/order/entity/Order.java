package com.prgrms.wadiz.domain.order.entity;

import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.global.BaseEntity;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supporter_id")
    private Supporter supporter;

    @OneToMany(mappedBy = "orderRewards", cascade = CascadeType.ALL)
    private List<OrderReward> orderRewards = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    public Order(
            Supporter supporter,
            OrderStatus orderStatus
    ) {
        this.supporter = supporter;
        this.orderStatus = orderStatus;
    }

    @Builder
    public Order(Long orderId, Supporter supporter, List<OrderReward> orderRewards, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.supporter = supporter;
        this.orderRewards = orderRewards;
        this.orderStatus = orderStatus;
    }

    public static Order createOrder(
            Supporter supporter,
            List<OrderReward> orderRewards
    ) {
        Order order = Order.builder()
                .supporter(supporter)
                .build();

        for(OrderReward orderReward : orderRewards){
            order.addOrderReward(orderReward);
        }

        order.setOrderStatus(OrderStatus.REQUESTED);

        return order;
    }

    //createOrder와 관련된 연관관계 편의 메서드
    public void addOrderReward(OrderReward orderReward) {
        orderRewards.add(orderReward);
        orderReward.changeOrder(this);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
