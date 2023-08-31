package com.prgrms.wadiz.domain.order.entity;

import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.global.BaseEntity;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
import lombok.AccessLevel;
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
}
