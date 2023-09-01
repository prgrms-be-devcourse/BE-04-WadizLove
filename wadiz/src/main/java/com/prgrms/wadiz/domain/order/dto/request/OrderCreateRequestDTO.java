package com.prgrms.wadiz.domain.order.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderCreateRequestDTO(List<OrderRewardRequestDTO> orderRewards) {

}
