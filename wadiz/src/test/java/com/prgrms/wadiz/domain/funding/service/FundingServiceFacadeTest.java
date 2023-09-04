package com.prgrms.wadiz.domain.funding.service;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.repository.FundingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        FundingCreateRequestDTO requestDTO = FundingCreateRequestDTO.builder()
                .fundingTargetAmount(1_000_000)
                .fundingCategory(FundingCategory.FOOD)
                .fundingStartAt(LocalDateTime.now())
                .fundingEndAt(LocalDateTime.now().plusWeeks(2L))
                .build();

        Funding expectedFunding = Funding.builder()
                .fundingCategory(requestDTO.fundingCategory())
                .fundingTargetAmount(requestDTO.fundingTargetAmount())
                .fundingStatus(FundingStatus.OPEN)
                .fundingStartAt(requestDTO.fundingStartAt())
                .fundingEndAt(requestDTO.fundingEndAt())
                .build();

        // mocking
        when(fundingRepository.save(any())).thenReturn(expectedFunding);

        // when
        Funding actualFunding = fundingServiceFacade.createFunding(requestDTO);

        // then
        assertThat(actualFunding, samePropertyValuesAs(expectedFunding));
    }

    @Test
    @DisplayName("[성공] Funding 정보 단건 조회")
    void getFunding() {
        // given
        Long fundingId = 1L;

        Funding funding = Funding.builder()
                .fundingCategory(FundingCategory.FOOD)
                .fundingTargetAmount(500_000)
                .fundingStatus(FundingStatus.OPEN)
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
        when(fundingRepository.findById(fundingId)).thenReturn(Optional.ofNullable(funding));

        // when
        FundingResponseDTO actualFundingResponseDTO = fundingServiceFacade.getFunding(fundingId);

        // then
        assertThat(actualFundingResponseDTO, samePropertyValuesAs(expectedFundingResponseDTO));
    }
}