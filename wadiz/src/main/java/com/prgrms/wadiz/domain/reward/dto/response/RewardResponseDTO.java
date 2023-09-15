package com.prgrms.wadiz.domain.reward.dto.response;

import com.prgrms.wadiz.domain.reward.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "리워드 응답 DTO")
@Builder
public record RewardResponseDTO(

        @Schema(description = "리워드 아이디")
        Long rewardId,
        @Schema(description = "리워드명")
        String rewardName,
        @Schema(description = "리워드 설명")
        String rewardDescription,
        @Schema(description = "리워드 재고")
        Integer rewardQuantity,
        @Schema(description = "리워드 가격")
        Integer rewardPrice,
        @Schema(description = "리워드 타입")
        RewardType rewardType,
        @Schema(description = "리워드 상태")
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
