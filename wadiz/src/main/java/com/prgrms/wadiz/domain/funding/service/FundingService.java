package com.prgrms.wadiz.domain.funding.service;

import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.repository.FundingRepository;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.project.repository.ProjectRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FundingService {
    private final FundingRepository fundingRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public Long createFunding(Long projectId, FundingCreateRequestDTO fundingCreateRequestDTO) {
        Project project = projectRepository.findById(projectId).orElseThrow();

        Funding funding = Funding.builder()
                .fundingCategory(fundingCreateRequestDTO.fundingCategory())
                .fundingTargetAmount(fundingCreateRequestDTO.fundingTargetAmount())
                .fundingStartAt(fundingCreateRequestDTO.fundingStartAt())
                .fundingEndAt(fundingCreateRequestDTO.fundingEndAt())
                .build();

        project.updateFunding(funding);

        return fundingRepository.save(funding).getFundingId();
    }

    @Transactional
    public FundingResponseDTO getFunding(Long fundingId) {
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new BaseException(ErrorCode.FUNDING_NOT_FOUND));

        return FundingResponseDTO.toResponseDTO(funding);
    }
}
