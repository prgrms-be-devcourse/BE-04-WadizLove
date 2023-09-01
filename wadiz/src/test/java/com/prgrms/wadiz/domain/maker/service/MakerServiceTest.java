package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerModifyRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.repository.MakerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
        maker = new Maker("test", "testBrand", "test@gmail.com");
    }

    @Test
    @DisplayName("메이커 회원가입을 성공한다.")
    void signUpMakerTest() {
        //given
        MakerCreateRequestDTO makerCreateRequestDTO = new MakerCreateRequestDTO(
                maker.getMakerName(),
                maker.getMakerBrand(),
                maker.getMakerEmail()
        );

        when(makerRepository.save(any(Maker.class))).then(AdditionalAnswers.returnsFirstArg());
        Maker maker1 = makerCreateRequestDTO.toEntity();

        //when
        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(makerCreateRequestDTO);
        Maker maker2 = makerResponseDTO.toEntity();

        //then
        assertThat(maker1.getMakerId()).isEqualTo(maker2.getMakerId());
    }

    @Test
    @DisplayName("메이커 정보를 수정한다.")
    void modifyMakerTest() {
        //given
        MakerCreateRequestDTO makerCreateRequestDTO = new MakerCreateRequestDTO(
                maker.getMakerName(),
                maker.getMakerBrand(),
                maker.getMakerEmail()
        );

        when(makerRepository.save(any(Maker.class))).then(AdditionalAnswers.returnsFirstArg());
        Maker maker1 = makerCreateRequestDTO.toEntity();
        Long makerId = maker1.getMakerId();

        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(makerCreateRequestDTO);

        MakerModifyRequestDTO makerModifyRequestDTO = new MakerModifyRequestDTO(
                "update",
                "updateBrand",
                "update@gmail.com"
        );

        when(makerRepository.findById(makerId)).thenReturn(Optional.of(maker1));


        //when
        MakerResponseDTO makerResponseDTO1 = makerService.modifyMaker(
                makerResponseDTO.toEntity().getMakerId(),
                makerModifyRequestDTO
        );

        //then
        assertThat(makerResponseDTO.toEntity().getMakerId()).
                isEqualTo(makerResponseDTO.toEntity().getMakerId());

        assertThat(makerResponseDTO1.makerName())
                .isEqualTo("update");
    }

    @Test
    @DisplayName("메이커 soft-delete 테스트를 한다.")
    void softDeleteTest() {
        //given
        MakerCreateRequestDTO makerCreateRequestDTO = new MakerCreateRequestDTO(
                maker.getMakerName(),
                maker.getMakerBrand(),
                maker.getMakerEmail()
        );

        when(makerRepository.save(any(Maker.class))).then(AdditionalAnswers.returnsFirstArg());

        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(makerCreateRequestDTO);
        Maker maker1 = makerResponseDTO.toEntity();
        Long makerId = maker1.getMakerId();

        //when
        makerService.deleteMaker(makerId);

        //then
        verify(makerRepository).deleteById(makerId);
    }
}