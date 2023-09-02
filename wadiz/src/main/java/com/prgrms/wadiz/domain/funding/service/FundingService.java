package com.prgrms.wadiz.domain.funding.service;

import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.repository.FundingRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FundingService {
    private final FundingRepository fundingRepository;

    @Transactional
    public Funding createFunding(FundingCreateRequestDTO fundingCreateRequestDTO) {
        Funding funding = Funding.builder()
                .fundingCategory(fundingCreateRequestDTO.fundingCategory())
                .fundingTargetAmount(fundingCreateRequestDTO.fundingTargetAmount())
                .fundingStatus(FundingStatus.OPEN)
                .fundingStartAt(fundingCreateRequestDTO.fundingStartAt())
                .fundingEndAt(fundingCreateRequestDTO.fundingEndAt())
                .build();

        return fundingRepository.save(funding);
    }

    @Transactional
    public FundingResponseDTO getFunding(Long fundingId) {
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new BaseException(ErrorCode.FUNDING_NOT_FOUND));

        return FundingResponseDTO.toResponseDTO(funding);
    }
}
