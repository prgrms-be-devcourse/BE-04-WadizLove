package com.prgrms.wadiz.domain.reward.dto.request;

import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;

public record RewardRequestDTO(
        String rewardName,
        String rewardDescription,
        Integer rewardQuantity,
        Integer rewardPrice,
        RewardType rewardType,
        RewardStatus rewardStatus
) {
}
