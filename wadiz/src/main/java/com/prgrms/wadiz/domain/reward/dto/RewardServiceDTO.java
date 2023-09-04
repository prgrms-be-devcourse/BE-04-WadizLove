package com.prgrms.wadiz.domain.reward.dto;

import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.service.RewardServiceFacade;

public record RewardServiceDTO(
        String rewardName,
        String rewardDescription,
        Integer rewardQuantity,
        Integer rewardPrice,
        RewardType rewardType,
        RewardStatus rewardStatus
) {
    public static Reward toEntity(RewardServiceDTO rewardServiceDTO) {
        return Reward.builder()
                .rewardName(rewardServiceDTO.rewardName)
                .rewardDescription(rewardServiceDTO.rewardDescription)
                .rewardQuantity(rewardServiceDTO.rewardQuantity)
                .rewardPrice(rewardServiceDTO.rewardPrice)
                .rewardType(rewardServiceDTO.rewardType)
                .build();
    }
}
