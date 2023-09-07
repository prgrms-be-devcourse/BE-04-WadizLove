package com.prgrms.wadiz.domain.funding.service;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingUpdateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.repository.FundingRepository;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FundingServiceFacadeTest {
    @InjectMocks
    private FundingServiceFacade fundingServiceFacade;
    @Mock
    private FundingRepository fundingRepository;

    @Test
    @DisplayName("[성공] Funding 정보 등록")
    void createFunding() {
        // given
        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.builder().build();

        FundingCreateRequestDTO requestDTO = FundingCreateRequestDTO.builder()
                .fundingTargetAmount(1_000_000)
                .fundingCategory(FundingCategory.FOOD)
                .fundingStartAt(LocalDateTime.now())
                .fundingEndAt(LocalDateTime.now().plusWeeks(2L))
                .build();

        Long fundingId = 1L;
        Funding expectedFunding = Funding.builder()
                .fundingCategory(requestDTO.fundingCategory())
                .fundingTargetAmount(requestDTO.fundingTargetAmount())
                .fundingStartAt(requestDTO.fundingStartAt())
                .fundingEndAt(requestDTO.fundingEndAt())
                .build();
        ReflectionTestUtils.setField(expectedFunding, "fundingId", fundingId);

        // mocking
        when(fundingRepository.save(any())).thenReturn(expectedFunding);
        when(fundingRepository.findById(fundingId)).thenReturn(Optional.of(expectedFunding));

        // when
        Long actualFundingId = fundingServiceFacade.createFunding(projectServiceDTO, requestDTO);

        // then
        Funding actualFunding = fundingRepository.findById(actualFundingId).get();

        assertThat(actualFunding, samePropertyValuesAs(expectedFunding));
    }

    @Test
    @DisplayName("[성공] Funding 정보 단건 조회")
    void getFundingByProjectId() {
        // given
        Long projectId = 1L;
        Project readyProject = Project.builder().build();

        Funding funding = Funding.builder()
                .project(readyProject)
                .fundingCategory(FundingCategory.FOOD)
                .fundingTargetAmount(500_000)
                .fundingStartAt(LocalDateTime.now())
                .fundingEndAt(LocalDateTime.now().plusWeeks(2L))
                .build();

        FundingResponseDTO expectedFundingResponseDTO = FundingResponseDTO.builder()
                .fundingCategory(FundingCategory.FOOD)
                .fundingTargetAmount(500_000)
                .fundingStatus(FundingStatus.OPEN)
                .fundingStartAt(LocalDateTime.now())
                .fundingEndAt(LocalDateTime.now().plusWeeks(2L))
                .build();

        // mocking
        when(fundingRepository.findByProjectId(projectId)).thenReturn(Optional.of(funding));

        // when
        FundingResponseDTO actualFundingResponseDTO = fundingServiceFacade.getFundingByProjectId(projectId);

        // then
        assertThat(actualFundingResponseDTO, samePropertyValuesAs(expectedFundingResponseDTO));
    }

    @Test
    @DisplayName("[성공] Funding 정보 수정")
    void updateFunding() {
        // given
        Long projectId = 1L;
        LocalDateTime fundingStartAt = LocalDateTime.now();
        LocalDateTime fundingEndAt = LocalDateTime.now().plusWeeks(2L);
        Project readyProject = Project.builder().build();

        Funding beforeFunding = Funding.builder()
                .project(readyProject)
                .fundingCategory(FundingCategory.FOOD)
                .fundingTargetAmount(500_000)
                .fundingStartAt(LocalDateTime.now())
                .fundingEndAt(LocalDateTime.now().plusWeeks(2L))
                .build();

        FundingUpdateRequestDTO fundingUpdateRequestDTO = new FundingUpdateRequestDTO(
                500_000,
                fundingStartAt,
                fundingEndAt,
                FundingCategory.FASHION,
                FundingStatus.OPEN
        );

        // mocking
        when(fundingRepository.findByProjectId(projectId)).thenReturn(Optional.of(beforeFunding));

        // when
        fundingServiceFacade.updateFunding(projectId, fundingUpdateRequestDTO);

        // then
        assertThat(fundingUpdateRequestDTO.fundingStatus(), is(beforeFunding.getFundingStatus()));
        assertThat(fundingUpdateRequestDTO.fundingTargetAmount(), is(beforeFunding.getFundingTargetAmount()));
        assertThat(fundingUpdateRequestDTO.fundingCategory(), is(beforeFunding.getFundingCategory()));
        assertThat(fundingUpdateRequestDTO.fundingStartAt(), is(beforeFunding.getFundingStartAt()));
        assertThat(fundingUpdateRequestDTO.fundingEndAt(), is(beforeFunding.getFundingEndAt()));
    }

    @Test
    @DisplayName("[예외] Project가 개설된 이후 Funding을 수정하려고 하면 예외가 발생한다.")
    void updateRFundingAfterProjectSetUp() {
        // given
        Long projectId = 1L;
        Project setUpProject = Project.builder().build();
        setUpProject.setUpProject();

        LocalDateTime fundingStartAt = LocalDateTime.now();
        LocalDateTime fundingEndAt = LocalDateTime.now().plusWeeks(2L);

        Funding setUpFunding = Funding.builder()
                .project(setUpProject)
                .fundingCategory(FundingCategory.FOOD)
                .fundingTargetAmount(500_000)
                .fundingStartAt(LocalDateTime.now())
                .fundingEndAt(LocalDateTime.now().plusWeeks(2L))
                .build();

        FundingUpdateRequestDTO fundingUpdateRequestDTO = new FundingUpdateRequestDTO(
                500_000,
                fundingStartAt,
                fundingEndAt,
                FundingCategory.FASHION,
                FundingStatus.OPEN
        );

        // mocking
        when(fundingRepository.findByProjectId(projectId)).thenReturn(Optional.of(setUpFunding));

        // when
        // then
        assertThatThrownBy(() -> fundingServiceFacade.updateFunding(projectId, fundingUpdateRequestDTO))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PROJECT_ACCESS_DENY);
    }

    @Test
    @DisplayName("[예외] Project가 개설된 이후 Funding을 삭제하려고 하면 예외가 발생한다.")
    void deleteFundingAfterProjectSetUp() {
        // given
        Long projectId = 1L;
        Project setUpProject = Project.builder().build();
        setUpProject.setUpProject();

        Funding setUpFunding = Funding.builder()
                .project(setUpProject)
                .fundingCategory(FundingCategory.FOOD)
                .fundingTargetAmount(500_000)
                .fundingStartAt(LocalDateTime.now())
                .fundingEndAt(LocalDateTime.now().plusWeeks(2L))
                .build();

        // mocking
        when(fundingRepository.findByProjectId(projectId)).thenReturn(Optional.of(setUpFunding));

        // when
        // then
        assertThatThrownBy(() -> fundingServiceFacade.deleteFunding(projectId))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PROJECT_ACCESS_DENY);
    }
}