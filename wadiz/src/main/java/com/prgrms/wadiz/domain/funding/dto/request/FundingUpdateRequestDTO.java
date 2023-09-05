package com.prgrms.wadiz.domain.funding.dto.request;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;

import java.time.LocalDateTime;

public record FundingUpdateRequestDTO(
        Integer fundingTargetAmount,
        LocalDateTime fundingStartAt,
        LocalDateTime fundingEndAt,
        FundingCategory fundingCategory,
        FundingStatus fundingStatus
) {
}
