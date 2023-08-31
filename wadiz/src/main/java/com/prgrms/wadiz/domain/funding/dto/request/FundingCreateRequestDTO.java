package com.prgrms.wadiz.domain.funding.dto.request;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public record FundingCreateRequestDTO(
        FundingCategory fundingCategory,
        Integer fundingTargetAmount,
        LocalDateTime fundingStartAt,
        LocalDateTime fundingEndAt
) {

}
