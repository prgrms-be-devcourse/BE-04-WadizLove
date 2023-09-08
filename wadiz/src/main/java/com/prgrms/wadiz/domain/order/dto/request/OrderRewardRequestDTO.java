package com.prgrms.wadiz.domain.order.dto.request;

import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
public record OrderRewardRequestDTO(
        @NotNull(message = "주문 상품 번호를 입력해주세요")
        Long rewardId,

        @Min(value = 1,message = "주문 수량은 1 이상입니다.")
        Integer orderQuantity
) {
}
