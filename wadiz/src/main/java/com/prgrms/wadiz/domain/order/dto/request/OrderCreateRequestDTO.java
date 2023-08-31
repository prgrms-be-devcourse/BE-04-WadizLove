package com.prgrms.wadiz.domain.order.dto.request;


import java.util.List;

public record OrderCreateRequestDTO(List<OrderRewardRequestDTO> orderRewards) {

}
