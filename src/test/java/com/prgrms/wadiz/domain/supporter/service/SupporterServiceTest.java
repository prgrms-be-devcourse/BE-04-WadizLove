package com.prgrms.wadiz.domain.supporter.service;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterUpdateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SupporterServiceTest {

    @Autowired
    private SupporterRepository supporterRepository;

    @Autowired
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
        SupporterCreateRequestDTO supporterDTO = new SupporterCreateRequestDTO(supporter.getName(),
                supporter.getEmail());

        //when
        SupporterResponseDTO supporterResponse = supporterService.createSupporter(supporterDTO);

        //then
        assertThat(supporterResponse).isNotNull();
    }

    @Test
    @DisplayName("서포터의 정보를 수정한다.")
    void updateTest() {
        //given
        SupporterCreateRequestDTO insertSupporter= new SupporterCreateRequestDTO(supporter.getName(),
                supporter.getEmail());

        SupporterResponseDTO responseDTO = supporterService.createSupporter(insertSupporter);

        SupporterUpdateRequestDTO supporterUpdateRequestDTO = new SupporterUpdateRequestDTO("update",
                "update@gmail.com");

        //when
        SupporterResponseDTO supporterResponseDTO = supporterService.updateSupporter(responseDTO.toEntity().getId(),
                supporterUpdateRequestDTO);

        //then
        assertThat(supporterResponseDTO.toEntity().getId()).isEqualTo(responseDTO.toEntity().getId());
        assertThat(supporterResponseDTO.name()).isEqualTo("update");
    }
}