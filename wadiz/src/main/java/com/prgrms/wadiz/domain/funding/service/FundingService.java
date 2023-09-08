package com.prgrms.wadiz.domain.funding.service;

import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingUpdateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.repository.FundingRepository;
import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FundingService {
    private final FundingRepository fundingRepository;

    @Transactional //TODO : 시작 날짜보다 끝나는 날짜가 더 이후여야 한다. (해당 예외 처리)
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

        if (!isProjectBeforeSetUp(funding.getProject())) {
            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        return FundingResponseDTO.from(funding);
    }

    @Transactional
    public void updateFunding(
            Long projectId,
            FundingUpdateRequestDTO fundingUpdateRequestDTO
    ) {
        Funding funding = fundingRepository.findByProjectId(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.FUNDING_NOT_FOUND));

        if (!isProjectBeforeSetUp(funding.getProject())) {
            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        funding.updateFunding(
                fundingUpdateRequestDTO.fundingTargetAmount(),
                fundingUpdateRequestDTO.fundingStartAt(),
                fundingUpdateRequestDTO.fundingEndAt(),
                fundingUpdateRequestDTO.fundingCategory(),
                fundingUpdateRequestDTO.fundingStatus()
        );
    }

    @Transactional
    public void deleteFunding(Long projectId) {
        Funding funding = fundingRepository.findByProjectId(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.FUNDING_NOT_FOUND));

        if (!isProjectBeforeSetUp(funding.getProject())) {
            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        fundingRepository.deleteByProjectId(projectId);
    }

    @Transactional(readOnly = true)
    public boolean isFundingExist(Long projectId) {
        return fundingRepository.findByProjectId(projectId).isPresent();
    }

    private boolean isProjectBeforeSetUp(Project project) {
        return project.getProjectStatus() == ProjectStatus.READY;
    }
}
