package com.prgrms.wadiz.domain.orderReward.dto.response;

import lombok.Builder;

@Builder
public record OrderRewardResponseDTO(
        String rewardName,
        String rewardDescription,
        Integer orderRewardPrice,
        Integer orderRewardQuantity
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
                .build();
    }
}
