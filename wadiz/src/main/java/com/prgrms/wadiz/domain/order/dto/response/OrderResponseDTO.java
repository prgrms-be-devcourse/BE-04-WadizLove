package com.prgrms.wadiz.domain.order.dto.response;

import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.orderReward.dto.response.OrderRewardResponseDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderResponseDTO(
        Long orderId,
        String postTitle,
        String makerBrand,
        List<OrderRewardResponseDTO> orderRewardResponseDTOs,
        OrderStatus orderStatus
) {
//    public static OrderResponseDTO from(Order order){
//        return OrderResponseDTO.builder()
//                .orderId(order.getOrderId())
//                .projectId(order.getProject().getProjectId())
//                .makerBrand(order.getProject().getMaker().getMakerBrand())
//                .orderRewards(order.getOrderRewards())
//                .orderStatus(order.getOrderStatus())
//                .build();
//    }

    public static OrderResponseDTO of(
            Long orderId,
            String postTitle,
            String makerBrand,
            List<OrderRewardResponseDTO> orderRewardResponseDTOs,
            OrderStatus orderStatus
    ){
        return OrderResponseDTO.builder()
                .orderId(orderId)
                .postTitle(postTitle)
                .makerBrand(makerBrand)
                .orderRewardResponseDTOs(orderRewardResponseDTOs)
                .orderStatus(orderStatus)
                .build();
    }
}