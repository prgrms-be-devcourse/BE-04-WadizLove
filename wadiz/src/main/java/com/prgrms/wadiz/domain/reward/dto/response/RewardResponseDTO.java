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
    public static RewardResponseDTO from(Reward reward){

        return RewardResponseDTO.builder()
                .rewardId(reward.getRewardId())
                .rewardName(reward.getRewardName())
                .rewardDescription(reward.getRewardDescription())
                .rewardQuantity(reward.getRewardQuantity())
                .rewardPrice(reward.getRewardPrice())
                .rewardType(reward.getRewardType())
                .rewardStatus(reward.getRewardStatus())
                .build();
    }
}
