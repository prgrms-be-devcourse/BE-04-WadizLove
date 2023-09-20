package com.prgrms.wadiz.domain.order.dto.request;

import com.prgrms.wadiz.domain.orderReward.dto.request.OrderRewardCreateRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "주문 생성 요청 DTO")
@Builder
public record OrderCreateRequestDTO(
        @Schema(description = "주문 생성 요청 DTO 리스트")
        List<OrderRewardCreateRequestDTO> orderRewards
) {
}
