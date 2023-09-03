package com.prgrms.wadiz.domain.reward.dto.response;

import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import lombok.Builder;

@Builder
public record RewardResponseDTO(
        Long rewardId,
        String rewardName,
        String rewardDescription,
        Integer rewardQuantity,
        Integer rewardPrice,
        RewardType rewardType,
        RewardStatus rewardStatus
) {
    public static RewardResponseDTO of(
            String rewardName,
            String rewardDescription,
            Integer rewardQuantity,
            Integer rewardPrice,
            RewardType rewardType,
            RewardStatus rewardStatus
    ){

        return RewardResponseDTO.builder()
                .rewardName(rewardName)
                .rewardDescription(rewardDescription)
                .rewardQuantity(rewardQuantity)
                .rewardPrice(rewardPrice)
                .rewardType(rewardType)
                .rewardStatus(rewardStatus)
                .build();
    }
}
