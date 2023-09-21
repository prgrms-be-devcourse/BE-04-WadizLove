package com.prgrms.wadiz.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prgrms.wadiz.domain.BaseEntity;
import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "주문")
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
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private List<OrderReward> orderRewards = new ArrayList<>();

    @Column(nullable = false)
    @NotNull(message = "총 주문 금액을 입력하세요")
    private Integer totalOrderPrice;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "주문 상태를 입력하세요")
    private OrderStatus orderStatus;

    @Builder
    public Order(
            Supporter supporter,
            Project project
    ) {
        this.supporter = supporter;
        this.project = project;
        this.orderStatus = OrderStatus.REQUESTED;
        this.totalOrderPrice = 0;
    }

    public void addOrderReward(OrderReward orderReward) {
        orderRewards.add(orderReward);
        orderReward.changeOrder(this);
    }

    public void calculateTotalOrderPrice(OrderReward orderReward){
        this.totalOrderPrice += orderReward.calculateOrderRewardPrice();
    }

    public void cancel() {
        this.setOrderStatus(OrderStatus.CANCELED);
        orderRewards.forEach(OrderReward::cancel);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
