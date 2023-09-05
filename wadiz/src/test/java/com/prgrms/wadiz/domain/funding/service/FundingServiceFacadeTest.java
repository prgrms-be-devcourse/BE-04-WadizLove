package com.prgrms.wadiz.domain.funding.service;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingUpdateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.repository.FundingRepository;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

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

        Funding funding = Funding.builder()
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

        Funding beforeFunding = Funding.builder()
                .fundingCategory(FundingCategory.FOOD)
                .fundingTargetAmount(500_000)
                .fundingStartAt(LocalDateTime.now())
                .fundingEndAt(LocalDateTime.now().plusWeeks(2L))
                .build();

        Funding afterFunding = Funding.builder()
                .fundingCategory(FundingCategory.FASHION)
                .fundingTargetAmount(500_000)
                .fundingStartAt(fundingStartAt)
                .fundingEndAt(fundingEndAt)
                .build();

        FundingUpdateRequestDTO fundingUpdateRequestDTO = new FundingUpdateRequestDTO(
                FundingCategory.FASHION,
                500_000,
                fundingStartAt,
                fundingEndAt,
                FundingStatus.OPEN
        );

        // mocking
        when(fundingRepository.findByProjectId(projectId)).thenReturn(Optional.of(beforeFunding));

        // when
        fundingServiceFacade.updateFunding(projectId, fundingUpdateRequestDTO);

        // then
        assertThat(fundingUpdateRequestDTO.fundingStatus(), is(afterFunding.getFundingStatus()));
        assertThat(fundingUpdateRequestDTO.fundingTargetAmount(), is(afterFunding.getFundingTargetAmount()));
        assertThat(fundingUpdateRequestDTO.fundingCategory(), is(afterFunding.getFundingCategory()));
        assertThat(fundingUpdateRequestDTO.fundingStartAt(), is(afterFunding.getFundingStartAt()));
        assertThat(fundingUpdateRequestDTO.fundingEndAt(), is(afterFunding.getFundingEndAt()));
    }
}