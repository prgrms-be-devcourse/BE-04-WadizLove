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

    /**
     * Funding 생성
     */
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
            log.warn("Funding end time is before start time");

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

    /**
     * Funding 조회
     */
    @Transactional(readOnly = true)
    public FundingResponseDTO getFundingByProjectId(Long projectId) {
        Funding funding = fundingRepository.findByProject_ProjectId(projectId)
                .orElseThrow(() -> {
                    log.warn("Funding is not found.");

                    throw new BaseException(ErrorCode.FUNDING_NOT_FOUND);
                });

        FundingStatus fundingStatus = validateFundingDeadline(funding);

        return FundingResponseDTO.of(funding,fundingStatus);
    }

    /**
     * Funding 정보 수정
     */
    @Transactional
    public void updateFunding(
            Long projectId,
            FundingUpdateRequestDTO fundingUpdateRequestDTO
    ) {
        if (!isFundingStartAtBeforeFundingEndAt(
                fundingUpdateRequestDTO.fundingStartAt(),
                fundingUpdateRequestDTO.fundingEndAt())
        ) {
            log.warn("Funding end time is before start time");

            throw new BaseException(ErrorCode.INVALID_FUNDING_DURATION);
        }

        Funding funding = fundingRepository.findByProject_ProjectId(projectId)  //TODO : 예외 로그
                .orElseThrow(() -> {
                    log.warn("Funding is not found.");

                    throw new BaseException(ErrorCode.FUNDING_NOT_FOUND);
                });

        if (!isProjectBeforeSetUp(funding.getProject())) {
            log.warn("Project's status is not 'before setUp'");

            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        funding.updateFunding(
                fundingUpdateRequestDTO.fundingTargetAmount(),
                fundingUpdateRequestDTO.fundingStartAt(),
                fundingUpdateRequestDTO.fundingEndAt(),
                FundingCategory.valueOf(fundingUpdateRequestDTO.fundingCategory())
        );
    }

    /**
     * Funding 삭제
     */
    @Transactional
    public void deleteFunding(Long projectId) {
        Funding funding = fundingRepository.findByProject_ProjectId(projectId)
                .orElseThrow(() -> {
                    log.warn("Funding is not found.");

                    throw new BaseException(ErrorCode.FUNDING_NOT_FOUND);
                });

        if (!isProjectBeforeSetUp(funding.getProject())) {
            log.warn("Project's status is not 'before setUp'");

            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        fundingRepository.deleteByProject_ProjectId(projectId);
    }

    /**
     * Funding 존재 여부 확인
     */
    @Transactional(readOnly = true)
    public boolean isFundingExist(Long projectId) {

        return fundingRepository.findByProject_ProjectId(projectId).isPresent();
    }

    /**
     * Project가 개설된 상태인지 확인
     */
    private boolean isProjectBeforeSetUp(Project project) {

        return project.getProjectStatus() == ProjectStatus.READY;
    }

    /**
     * Funding 마감 시간은 시작 시간보다 이전이면 안된다.
     */
    private boolean isFundingStartAtBeforeFundingEndAt(
            LocalDateTime fundingStartAt,
            LocalDateTime fundingEndAt
    ) {

       return fundingStartAt.isBefore(fundingEndAt);
    }

    /**
     * Funding 마감일자 검증
     */
    private FundingStatus validateFundingDeadline(Funding funding){
        if (funding.getFundingEndAt().isBefore(LocalDateTime.now())) {

            return FundingStatus.CLOSED;
        }

        return FundingStatus.OPENED;
    }
}
