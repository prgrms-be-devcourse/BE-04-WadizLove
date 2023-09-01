package com.prgrms.wadiz.domain.supporter.service;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterUpdateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupporterServiceTest {

    @Mock
    private SupporterRepository supporterRepository;

    @InjectMocks
    private SupporterService supporterService;

    private Supporter supporter;

    @BeforeEach
    void setUp() {
        supporter = new Supporter("testName", "test");
    }

    @Test
    @DisplayName("서포터를 생성하여 저장한다")
    void saveTest() {
        //given
        SupporterCreateRequestDTO supporterDTO = new SupporterCreateRequestDTO(
                supporter.getName(),
                supporter.getEmail()
        );

        when(supporterRepository.save(any(Supporter.class))).then(AdditionalAnswers.returnsFirstArg());

        Supporter supporter1 = supporterDTO.toEntity();

        //when
        SupporterResponseDTO supporterResponse = supporterService.createSupporter(supporterDTO);
        Supporter supporter2 = supporterResponse.toEntity();

        //then
        assertThat(supporter1.getId()).isEqualTo(supporter2.getId());
    }

    @Test
    @DisplayName("서포터의 정보를 수정한다.")
    void updateTest() {
        //given
        SupporterCreateRequestDTO supporterDTO= new SupporterCreateRequestDTO(
                supporter.getName(),
                supporter.getEmail()
        );

        when(supporterRepository.save(any(Supporter.class))).then(AdditionalAnswers.returnsFirstArg());

        SupporterResponseDTO responseDTO = supporterService.createSupporter(supporterDTO);
        Supporter supporter1 = responseDTO.toEntity();
        Long supporter1Id = supporter1.getId();

        SupporterUpdateRequestDTO supporterUpdateRequestDTO = new SupporterUpdateRequestDTO(
                "update",
                "update@gmail.com"
        );

        when(supporterRepository.findById(supporter1Id)).thenReturn(Optional.of(supporter1));

        //when
        SupporterResponseDTO supporterResponseDTO = supporterService.updateSupporter(
                supporter1.getId(),
                supporterUpdateRequestDTO
        );

        //then
        assertThat(supporterResponseDTO.toEntity().getId())
                .isEqualTo(supporter1.getId());

        assertThat(supporterResponseDTO.name())
                .isEqualTo("update");
    }

    @Test
    @DisplayName("서포터를 soft-delete 한다.")
    void softDeleteTest() {
        //given
        SupporterCreateRequestDTO supporterDTO = new SupporterCreateRequestDTO(
                supporter.getName(),
                supporter.getEmail()
        );

        when(supporterRepository.save(any(Supporter.class))).then(AdditionalAnswers.returnsFirstArg());

        SupporterResponseDTO responseDTO = supporterService.createSupporter(supporterDTO);
        Supporter supporter1 = responseDTO.toEntity();
        Long supporter1Id = supporter1.getId();

        //when
        supporterService.deleteSupporter(supporter1Id);

        //then
        verify(supporterRepository).deleteById(supporter1Id);
    }
}