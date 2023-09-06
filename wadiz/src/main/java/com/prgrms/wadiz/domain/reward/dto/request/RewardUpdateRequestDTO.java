package com.prgrms.wadiz.domain.reward.dto.request;

import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
public record RewardUpdateRequestDTO(
        @NotBlank(message = "리워드명을 입력해주세요.")
        String rewardName,

        @NotBlank(message = "리워드 설명을 입력해주세요.")
        String rewardDescription,

        @NotBlank(message = "리워드 재고를 입력해주세요.") @Min(1)
        Integer rewardQuantity,

        @NotBlank(message = "리워드 가격을 입력해주세요.") @Min(10)
        Integer rewardPrice,

        @NotBlank(message = "리워드 타입을 입력해주세요.")
        RewardType rewardType,

        @NotBlank(message = "리워드 상태를 입력해주세요.")
        RewardStatus rewardStatus
) {
}
