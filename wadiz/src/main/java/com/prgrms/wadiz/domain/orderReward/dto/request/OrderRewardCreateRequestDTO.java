package com.prgrms.wadiz.domain.orderReward.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Schema(description = "주문 상품 요청 DTO")
@Builder
public record OrderRewardCreateRequestDTO(
        @Schema(
                description = "주문 상품 id 값",
                example = "1"
        )
        @NotBlank(message = "주문 상품 번호를 입력해주세요.")
        Long rewardId,

        @Schema(
                description = "주문 상품 수량",
                example = "10"
        )
        @Min(
                value = 1,
                message = "주문 수량은 1 이상입니다."
        )
        @NotBlank(message = "주문 수량을 입력해주세요")
        Integer orderQuantity
) {
}
