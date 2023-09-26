package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.MakerStatus;
import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.respository.MakerRepository;
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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MakerServiceTest {

    @Mock
    MakerRepository makerRepository;

    @InjectMocks
    MakerService makerService;

    Maker maker;

    @BeforeEach
    void setUp() {
        maker = Maker.builder()
                .makerName("test")
                .makerBrand("testBrand")
                .makerEmail("test@gmail.com")
                .build();
    }

    @Test
    @DisplayName("[성공] 메이커 회원가입을 성공한다.")
    void signUpMakerTest() {
        //given
        MakerCreateRequestDTO makerCreateRequestDTO = MakerCreateRequestDTO.builder()
                .makerName(maker.getMakerName())
                .makerBrand(maker.getMakerBrand())
                .makerEmail(maker.getMakerEmail())
                .build();

        when(makerRepository.save(any(Maker.class))).then(AdditionalAnswers.returnsFirstArg());

        //when
        Long makerId = makerService.signUpMaker(makerCreateRequestDTO);

        //then
        assertThat(makerId).isNotNull();
    }

    @Test
    @DisplayName("[성공] 메이커 정보를 수정한다.")
    void modifyMakerTest() {
        //given
        when(makerRepository.findById(maker.getMakerId())).thenReturn(Optional.of(maker));

        MakerUpdateRequestDTO makerUpdateRequestDTO = MakerUpdateRequestDTO.builder()
                .makerName("update")
                .makerBrand("updateBrand")
                .makerEmail("update@gmail.com")
                .build();

        //when
        MakerResponseDTO makerUpdateResponse = makerService.updateMaker(maker.getMakerId(), makerUpdateRequestDTO);

        //then
        assertThat(makerUpdateResponse.makerName()).isEqualTo("update");
    }

    @Test
    @DisplayName("[성공] 메이커 soft-delete 테스트를 한다.")
    void softDeleteTest() {
        //given
        when(makerRepository.findById(maker.getMakerId())).thenReturn(Optional.of(maker));

        //when
        makerService.deleteMaker(maker.getMakerId());

        //then
        assertThat(maker.getStatus()).isEqualTo(MakerStatus.UNREGISTERED);
    }

    @Test
    @DisplayName("[실패] 중복된 이메일일 경우 예외가 발생한다.")
    void duplicatedEmailTest() {
        //given
        when(makerRepository.existsByMakerEmail(maker.getMakerEmail())).thenReturn(Boolean.TRUE);

        MakerCreateRequestDTO newMakerCreateRequestDTO = MakerCreateRequestDTO.builder()
                .makerName("other")
                .makerBrand(maker.getMakerBrand())
                .makerEmail(maker.getMakerEmail())
                .build();


        //when & then
        assertThatThrownBy(() -> {
            makerService.signUpMaker(newMakerCreateRequestDTO);
        }).isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.DUPLICATED_EMAIL);

    }

    @Test
    @DisplayName("[실패] 중복된 이름일 경우 예외가 발생한다.")
    void duplicatedNameTest() {
        //given
        when(makerRepository.existsByMakerName(maker.getMakerName())).thenReturn(Boolean.TRUE);

        MakerCreateRequestDTO newMakerCreateRequestDTO = MakerCreateRequestDTO.builder()
                .makerName(maker.getMakerName())
                .makerBrand(maker.getMakerBrand())
                .makerEmail("other@naver.com")
                .build();

        when(makerRepository.save(any(Maker.class))).then(AdditionalAnswers.returnsFirstArg());

        //when & then
        assertThatThrownBy(() -> {
            makerService.signUpMaker(newMakerCreateRequestDTO);
        }).isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.DUPLICATED_NAME);
    }
}