package com.prgrms.wadiz.domain.funding.dto.response;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FundingResponseDTO(
        Long fundingId,
        FundingCategory fundingCategory,
        Integer fundingTargetAmount,
        LocalDateTime fundingStartAt,
        LocalDateTime fundingEndAt,
        FundingStatus fundingStatus
) {
    public static FundingResponseDTO toResponseDTO(Funding funding) {
        return FundingResponseDTO.builder()
                .fundingId(funding.getFundingId())
                .fundingCategory(funding.getFundingCategory())
                .fundingTargetAmount(funding.getFundingTargetAmount())
                .fundingStartAt(funding.getFundingStartAt())
                .fundingEndAt(funding.getFundingEndAt())
                .fundingStatus(funding.getFundingStatus())
                .build();
    }
}
