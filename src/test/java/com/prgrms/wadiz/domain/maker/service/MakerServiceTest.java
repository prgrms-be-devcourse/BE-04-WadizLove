package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.repository.MakerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
        MakerCreateRequestDTO makerCreateRequestDTO = new MakerCreateRequestDTO(maker.getMakerName(), maker.getMakerBrand(), maker.getMakerEmail());

        //when
        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(makerCreateRequestDTO);

        //then
        assertThat(makerResponseDTO).isNotNull();
    }
}