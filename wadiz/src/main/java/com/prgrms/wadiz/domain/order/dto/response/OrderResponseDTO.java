package com.prgrms.wadiz.domain.order.dto.response;

import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderResponseDTO(
        Long orderId,
        Supporter supporter,
        List<OrderReward> orderRewards,
        OrderStatus orderStatus
) {
    public static OrderResponseDTO from(Order order){
        return OrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .supporter(order.getSupporter())
                .orderRewards(order.getOrderRewards())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}