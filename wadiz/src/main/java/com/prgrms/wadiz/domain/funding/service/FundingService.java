package com.prgrms.wadiz.domain.funding.service;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;

    @Transactional
    public Long createFunding(
            ProjectServiceDTO projectServiceDTO,
            FundingCreateRequestDTO fundingCreateRequestDTO
    ) {
        Project project = ProjectServiceDTO.toEntity(projectServiceDTO);

        if (!isFundingStartAtBeforeFundingEndAt(
                fundingCreateRequestDTO.fundingStartAt(),
                fundingCreateRequestDTO.fundingEndAt())
        ) {
            throw new BaseException(ErrorCode.INVALID_FUNDING_DURATION);
        }

        Funding funding = Funding.builder()
                .project(project)
                .fundingTargetAmount(fundingCreateRequestDTO.fundingTargetAmount())
                .fundingStartAt(fundingCreateRequestDTO.fundingStartAt())
                .fundingCategory(FundingCategory.valueOf(fundingCreateRequestDTO.fundingCategory()))
                .fundingEndAt(fundingCreateRequestDTO.fundingEndAt())
                .build();

        return fundingRepository.save(funding).getFundingId();
    }

    @Transactional(readOnly = true)
    public FundingResponseDTO getFundingByProjectId(Long projectId) {
        Funding funding = fundingRepository.findByProject_ProjectId(projectId) //TODO : 예외 로그
                .orElseThrow(() -> new BaseException(ErrorCode.FUNDING_NOT_FOUND));

        FundingStatus fundingStatus = validateFundingDeadline(funding);

        return FundingResponseDTO.of(funding,fundingStatus);
    }

    @Transactional
    public void updateFunding(
            Long projectId,
            FundingUpdateRequestDTO fundingUpdateRequestDTO
    ) {
        if (!isFundingStartAtBeforeFundingEndAt(
                fundingUpdateRequestDTO.fundingStartAt(),
                fundingUpdateRequestDTO.fundingEndAt())
        ) {
            throw new BaseException(ErrorCode.INVALID_FUNDING_DURATION);
        }

        Funding funding = fundingRepository.findByProject_ProjectId(projectId)  //TODO : 예외 로그
                .orElseThrow(() -> new BaseException(ErrorCode.FUNDING_NOT_FOUND));

        if (!isProjectBeforeSetUp(funding.getProject())) {
            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        funding.updateFunding(
                fundingUpdateRequestDTO.fundingTargetAmount(),
                fundingUpdateRequestDTO.fundingStartAt(),
                fundingUpdateRequestDTO.fundingEndAt(),
                FundingCategory.valueOf(fundingUpdateRequestDTO.fundingCategory())
        );
    }

    @Transactional
    public void deleteFunding(Long projectId) {
        Funding funding = fundingRepository.findByProject_ProjectId(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.FUNDING_NOT_FOUND));  //TODO : 예외 로그

        if (!isProjectBeforeSetUp(funding.getProject())) {  //TODO : 예외 로그

            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        fundingRepository.deleteByProject_ProjectId(projectId);
    }

    @Transactional(readOnly = true)
    public boolean isFundingExist(Long projectId) {

        return fundingRepository.findByProject_ProjectId(projectId).isPresent();
    }

    private boolean isProjectBeforeSetUp(Project project) {

        return project.getProjectStatus() == ProjectStatus.READY;
    }

    private boolean isFundingStartAtBeforeFundingEndAt(
            LocalDateTime fundingStartAt,
            LocalDateTime fundingEndAt
    ) {

       return fundingStartAt.isBefore(fundingEndAt);
    }

    private FundingStatus validateFundingDeadline(Funding funding){
        if (funding.getFundingEndAt().isBefore(LocalDateTime.now())) {

            return FundingStatus.CLOSED;
        }

        return FundingStatus.OPENED;
    }
}
