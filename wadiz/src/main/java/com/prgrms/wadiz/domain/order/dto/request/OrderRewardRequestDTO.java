package com.prgrms.wadiz.domain.order.dto.request;

import lombok.Builder;

@Builder
public record OrderRewardRequestDTO(
        Long rewardId,
        Integer orderQuantity
) {
}
