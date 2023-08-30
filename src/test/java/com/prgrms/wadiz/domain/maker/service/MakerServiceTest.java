package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerModifyRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.repository.MakerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MakerServiceTest {

    @Autowired
    MakerRepository makerRepository;

    @Autowired
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
        MakerCreateRequestDTO makerCreateRequestDTO = new MakerCreateRequestDTO(maker.getMakerName(),
                maker.getMakerBrand(), maker.getMakerEmail());

        //when
        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(makerCreateRequestDTO);

        //then
        assertThat(makerResponseDTO).isNotNull();
    }

    @Test
    @DisplayName("메이커 정보를 수정한다.")
    void modifyMakerTest() {
        //given
        MakerCreateRequestDTO makerCreateRequestDTO = new MakerCreateRequestDTO(maker.getMakerName(),
                maker.getMakerBrand(), maker.getMakerEmail());
        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(makerCreateRequestDTO);

        MakerModifyRequestDTO makerModifyRequestDTO = new MakerModifyRequestDTO("update",
                "updateBrand", "update@gmail.com");

        //when
        MakerResponseDTO makerResponseDTO1 = makerService.modifyMaker(makerResponseDTO.toEntity().getMakerId(), makerModifyRequestDTO);

        //then
        assertThat(makerResponseDTO.toEntity().getMakerId()).isEqualTo(makerResponseDTO.toEntity().getMakerId());
        assertThat(makerResponseDTO1.makerName()).isEqualTo("update");
    }

    @Test
    @DisplayName("메이커 soft delete 테스트를 한다.")
    void softDeleteTest() {
        //given
        MakerCreateRequestDTO makerCreateRequestDTO = new MakerCreateRequestDTO(maker.getMakerName(),
                maker.getMakerBrand(), maker.getMakerEmail());
        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(makerCreateRequestDTO);

        //when
        makerService.deleteMaker(makerResponseDTO.toEntity().getMakerId());

        //then
        assertTrue(makerRepository.findById(makerResponseDTO.toEntity().getMakerId()).get().isDeleted());
    }
}