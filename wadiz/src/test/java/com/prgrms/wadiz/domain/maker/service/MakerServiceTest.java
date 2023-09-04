package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MakerServiceFacadeTest {

    @Mock
    MakerRepository makerRepository;

    @InjectMocks
    MakerServiceFacade makerServiceFacade;

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
    @DisplayName("메이커 회원가입을 성공한다.")
    void signUpMakerTest() {
        //given
        MakerCreateRequestDTO makerCreateRequestDTO = MakerCreateRequestDTO.builder()
                .makerName(maker.getMakerName())
                .makerBrand(maker.getMakerBrand())
                .makerEmail(maker.getMakerEmail())
                .build();

        when(makerRepository.save(any(Maker.class))).then(AdditionalAnswers.returnsFirstArg());

        //when
<<<<<<< HEAD:wadiz/src/test/java/com/prgrms/wadiz/domain/maker/service/MakerServiceTest.java
        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(makerCreateRequestDTO);
=======
        MakerResponseDTO makerResponseDTO = makerServiceFacade.signUpMaker(makerCreateRequestDTO);
        Maker maker2 = makerResponseDTO.toEntity();
>>>>>>> fix:wadiz/src/test/java/com/prgrms/wadiz/domain/maker/service/MakerServiceFacadeTest.java

        //then
        assertThat(makerResponseDTO).isNotNull();
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

        MakerResponseDTO makerResponseDTO = makerServiceFacade.signUpMaker(makerCreateRequestDTO);

        MakerUpdateRequestDTO makerUpdateRequestDTO = MakerUpdateRequestDTO.builder()
                .makerName("update")
                .makerBrand("updateBrand")
                .makerEmail("update@gmail.com")
                .build();

        //when
<<<<<<< HEAD:wadiz/src/test/java/com/prgrms/wadiz/domain/maker/service/MakerServiceTest.java
        MakerResponseDTO makerUpdateResponse = makerService.updateMaker(maker.getMakerId(), makerUpdateRequestDTO);
=======
        MakerResponseDTO makerResponseDTO1 = makerServiceFacade.modifyMaker(
                makerResponseDTO.toEntity().getMakerId(),
                makerModifyRequestDTO
        );
>>>>>>> fix:wadiz/src/test/java/com/prgrms/wadiz/domain/maker/service/MakerServiceFacadeTest.java

        //then
        assertThat(makerUpdateResponse.makerName()).isEqualTo("update");
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

<<<<<<< HEAD:wadiz/src/test/java/com/prgrms/wadiz/domain/maker/service/MakerServiceTest.java
        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(makerCreateRequestDTO);

        //when
        makerService.deleteMaker(maker.getMakerId());
=======
        MakerResponseDTO makerResponseDTO = makerServiceFacade.signUpMaker(makerCreateRequestDTO);
        Maker maker1 = makerResponseDTO.toEntity();
        Long makerId = maker1.getMakerId();

        //when
        makerServiceFacade.deleteMaker(makerId);
>>>>>>> fix:wadiz/src/test/java/com/prgrms/wadiz/domain/maker/service/MakerServiceFacadeTest.java

        //then
        verify(makerRepository).deleteById(maker.getMakerId());
    }
}