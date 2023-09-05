package com.prgrms.wadiz.domain.funding.service;

import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingUpdateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.repository.FundingRepository;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FundingServiceFacade {
    private final FundingRepository fundingRepository;

    @Transactional
    public Long createFunding(
            ProjectServiceDTO projectServiceDTO,
            FundingCreateRequestDTO fundingCreateRequestDTO
    ) {
        Project project = ProjectServiceDTO.toEntity(projectServiceDTO);

        Funding funding = Funding.builder()
                .project(project)
                .fundingCategory(fundingCreateRequestDTO.fundingCategory())
                .fundingTargetAmount(fundingCreateRequestDTO.fundingTargetAmount())
                .fundingStartAt(fundingCreateRequestDTO.fundingStartAt())
                .fundingEndAt(fundingCreateRequestDTO.fundingEndAt())
                .build();

        return fundingRepository.save(funding).getFundingId();
    }

    @Transactional(readOnly = true)
    public FundingResponseDTO getFundingByProjectId(Long projectId) {
        Funding funding = fundingRepository.findByProjectId(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.FUNDING_NOT_FOUND));

        return FundingResponseDTO.from(funding);
    }

    @Transactional
    public void updateFunding(
            Long projectId,
            FundingUpdateRequestDTO fundingUpdateRequestDTO
    ) {
        Funding funding = fundingRepository.findByProjectId(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.FUNDING_NOT_FOUND));

        funding.updateFunding(
                fundingUpdateRequestDTO.fundingStatus(),
                fundingUpdateRequestDTO.fundingTargetAmount(),
                fundingUpdateRequestDTO.fundingCategory(),
                fundingUpdateRequestDTO.fundingStartAt(),
                fundingUpdateRequestDTO.fundingEndAt()
        );
    }

    @Transactional(readOnly = true)
    public boolean isFundingExist(Long projectId) {
        return fundingRepository.findByProjectId(projectId).isPresent();
    }

    @Transactional
    public void deleteFunding(Long projectId) {
        fundingRepository.deleteByProjectId(projectId);
    }
}