package com.prgrms.wadiz.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.BaseEntity;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
import com.prgrms.wadiz.global.annotation.ValidEnum;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderReward> orderRewards = new ArrayList<>();

    @ValidEnum(enumClass = OrderStatus.class, message = "존재하지 않는 상태입니다.")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    public Order(
            Supporter supporter,
            Project project
    ) {
        this.supporter = supporter;
        this.project = project;
        this.orderStatus = OrderStatus.REQUESTED;
    }

    public void addOrderReward(OrderReward orderReward) {
        orderRewards.add(orderReward);
        orderReward.changeOrder(this);
    }

    public void cancel() {
        this.setOrderStatus(OrderStatus.CANCELED);
        orderRewards.forEach(OrderReward::cancel);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


}
