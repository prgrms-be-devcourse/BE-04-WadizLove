package com.prgrms.wadiz.domain.reward.dto.request;

import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.global.annotation.ValidEnum;
import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
public record RewardUpdateRequestDTO(
        @NotBlank(message = "리워드명을 입력해주세요.")
        String rewardName,

        @NotBlank(message = "리워드 설명을 입력해주세요.")
        String rewardDescription,

        @Min(value = 1, message = "리워드 재고는 최소 1개 이상입니다.")
        Integer rewardQuantity,

        @Min(value = 10, message = "리워드 가격을 입력해주세요.")
        Integer rewardPrice,

        @ValidEnum(enumClass = RewardType.class, message = "리워드 타입을 입력해주세요.")
        RewardType rewardType,

        @ValidEnum(enumClass = RewardStatus.class, message = "리워드 상태를 입력해주세요.")
        RewardStatus rewardStatus
) {
}
