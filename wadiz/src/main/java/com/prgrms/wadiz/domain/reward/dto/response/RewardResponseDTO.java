package com.prgrms.wadiz.domain.reward.dto.response;

import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import io.swagger.annotations.ApiParam;
import lombok.Builder;

@Builder
public record RewardResponseDTO(
        @ApiParam(value = "리워드 아이디")
        Long rewardId,
        @ApiParam(value = "리워드명")
        String rewardName,
        @ApiParam(value = "리워드 설명")
        String rewardDescription,
        @ApiParam(value = "리워드 재고")
        Integer rewardQuantity,
        @ApiParam(value = "리워드 가격")
        Integer rewardPrice,
        @ApiParam(value = "리워드 타입")
        RewardType rewardType,
        @ApiParam(value = "리워드 상태")
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
