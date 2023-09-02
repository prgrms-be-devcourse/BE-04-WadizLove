package com.prgrms.wadiz.domain.reward.dto.request;

import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.domain.reward.entity.Reward;

public record RewardCreateRequestDTO(
        String rewardName,
        String rewardDescription,
        Integer rewardQuantity,
        Integer rewardPrice,
        RewardType rewardType,
        RewardStatus rewardStatus
) {
    public Reward toEntity() {
        return Reward.builder()
                .rewardName(rewardName)
                .rewardDescription(rewardDescription)
                .rewardQuantity(rewardQuantity)
                .rewardPrice(rewardPrice)
                .rewardType(rewardType)
                .rewardStatus(rewardStatus)
                .build();
    }

}
