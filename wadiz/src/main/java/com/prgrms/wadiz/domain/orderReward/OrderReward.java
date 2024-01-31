package com.prgrms.wadiz.domain.orderReward;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prgrms.wadiz.domain.BaseEntity;
import com.prgrms.wadiz.domain.order.Order;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Slf4j
@Entity
@Getter
@Table(name = "order_rewards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderReward extends BaseEntity {
    private static final int POSITIVE_ORDER_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderRewardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id")
    private Reward reward;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull(message = "주문 리워드의 가격이 필요합니다.")
    @Column(nullable = false)
    private Integer orderRewardPrice;

    @NotNull(message = "주문하는 리워드의 수량이 필요합니다.")
    @Column(nullable = false)
    private Integer orderRewardQuantity;

    @Builder
    public OrderReward(
            Reward reward,
            Integer orderRewardPrice,
            Integer orderRewardQuantity
    ) {
        this.reward = reward;
        this.orderRewardPrice = orderRewardPrice;
        this.orderRewardQuantity = validatePositive(orderRewardQuantity);
    }

    public void changeOrder(Order order) {
        this.order = order;
    }

    public void cancel() {
        getReward().addQuantity(orderRewardQuantity);

    }

    public Integer calculateOrderRewardPrice(){
        return this.orderRewardQuantity * this.orderRewardPrice;
    }

    private Integer validatePositive(Integer orderRewardQuantity) {
        if(orderRewardQuantity < POSITIVE_ORDER_QUANTITY){
            throw new BaseException(ErrorCode.ORDER_COUNT_ERROR);
        }

        return orderRewardQuantity;
    }
}
