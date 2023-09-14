package com.prgrms.wadiz.domain.orderReward.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "주문 상품 관련 응답 DTO")
@Builder
public record OrderRewardResponseDTO(
        @Schema(description = "주문 상품 이름")
        String rewardName,

        @Schema(description = "주문 상품 설명")
        String rewardDescription,

        @Schema(description = "주문 상품 가격")
        Integer orderRewardPrice,

        @Schema(description = "주문 상품 수량")
        Integer orderRewardQuantity,

        @Schema(description = "주문 상품 당 주문 가격")
        Integer totalOrderRewardPrice
){
    public static OrderRewardResponseDTO of(
            String rewardName,
            String rewardDescription,
            Integer orderRewardPrice,
            Integer orderRewardQuantity
    ){
        return OrderRewardResponseDTO
                .builder()
                .rewardName(rewardName)
                .rewardDescription(rewardDescription)
                .orderRewardPrice(orderRewardPrice)
                .orderRewardQuantity(orderRewardQuantity)
                .totalOrderRewardPrice(orderRewardPrice * orderRewardQuantity)
                .build();
    }
}
