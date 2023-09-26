package com.prgrms.wadiz.domain.supporter.service;

import com.prgrms.wadiz.domain.supporter.SupporterStatus;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterUpdateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupporterServiceTest {

    @Mock
    SupporterRepository supporterRepository;

    @InjectMocks
    SupporterService supporterService;

    Supporter supporter;

    @BeforeEach
    void setUp() {
        supporter = Supporter.builder()
                .supporterName("test")
                .supporterEmail("test@gmail.com")
                .build();
    }

    @Test
    @DisplayName("[성공] 서포터 회원가입을 성공한다.")
    void signUpMakerTest() {
        //given
        SupporterCreateRequestDTO supporterCreateRequestDTO = SupporterCreateRequestDTO.builder()
                .supporterName(supporter.getSupporterName())
                .supporterEmail(supporter.getSupporterEmail())
                .build();

        when(supporterRepository.save(any(Supporter.class))).then(AdditionalAnswers.returnsFirstArg());

        //when
        Long supporterId = supporterService.signUpSupporter(supporterCreateRequestDTO);

        //then
        assertThat(supporterId).isNotNull();
    }

    @Test
    @DisplayName("[성공] 서포터 정보를 수정한다.")
    void modifyMakerTest() {
        //given
        when(supporterRepository.findById(supporter.getSupporterId())).thenReturn(Optional.of(supporter));

        SupporterUpdateRequestDTO supporterUpdateRequestDTO = SupporterUpdateRequestDTO.builder()
                .supporterName("update")
                .supporterEmail("update@gmail.com")
                .build();

        //when
        SupporterResponseDTO supporterUpdateResponse = supporterService.updateSupporter(supporter.getSupporterId(), supporterUpdateRequestDTO);

        //then
        assertThat(supporterUpdateResponse.supporterName()).isEqualTo("update");
    }

    @Test
    @DisplayName("[성공] 메이커 soft-delete 테스트를 한다.")
    void softDeleteTest() {
        //given
        when(supporterRepository.findById(supporter.getSupporterId())).thenReturn(Optional.of(supporter));

        //when
        supporterService.deleteSupporter(supporter.getSupporterId());

        //then
        assertThat(supporter.getStatus()).isEqualTo(SupporterStatus.UNREGISTERED);
    }

    @Test
    @DisplayName("[실패] 중복된 이메일일 경우 예외가 발생한다.")
    void duplicatedEmailTest() {
        //given
        when(supporterRepository.existsBySupporterEmail(supporter.getSupporterEmail())).thenReturn(Boolean.TRUE);

        SupporterCreateRequestDTO newSupporterCreateRequestDTO = SupporterCreateRequestDTO.builder()
                .supporterName("other")
                .supporterEmail(supporter.getSupporterEmail())
                .build();

        //when & then
        assertThatThrownBy(() -> {
            supporterService.signUpSupporter(newSupporterCreateRequestDTO);
        }).isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.DUPLICATED_EMAIL);
    }

    @Test
    @DisplayName("[실패] 중복된 이름일 경우 예외가 발생한다.")
    void duplicatedNameTest() {
        //given
        when(supporterRepository.existsBySupporterName(supporter.getSupporterName())).thenReturn(Boolean.TRUE);

        SupporterCreateRequestDTO newSupporterCreateRequestDTO = SupporterCreateRequestDTO.builder()
                .supporterName(supporter.getSupporterName())
                .supporterEmail("other@naver.com")
                .build();

        //when & then
        assertThatThrownBy(() -> {
            supporterService.signUpSupporter(newSupporterCreateRequestDTO);
        }).isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.DUPLICATED_NAME);
    }
}