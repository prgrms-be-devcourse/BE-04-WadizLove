package com.prgrms.wadiz.domain.funding.dto.request;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FundingCreateRequestDTO(
        Integer fundingTargetAmount,
        LocalDateTime fundingStartAt,
        LocalDateTime fundingEndAt,
        FundingCategory fundingCategory
) {

}
