package com.prgrms.wadiz.domain.funding.dto.request;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FundingCreateRequestDTO(
        FundingCategory fundingCategory,
        Integer fundingTargetAmount,
        LocalDateTime fundingStartAt,
        LocalDateTime fundingEndAt
) {

}
