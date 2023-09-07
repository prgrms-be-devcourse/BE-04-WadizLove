package com.prgrms.wadiz.domain.maker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.service.MakerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MakerController.class)
class MakerControllerTest {

    @MockBean
    private MakerService makerService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private Maker maker;

    private final String code = "$.[?(@.code == '%s')]";

    private final String message = "$.[?(@.message == '%s')]";

    @BeforeEach
    void setUp() {
        maker = Maker.builder()
                .makerName("test")
                .makerBrand("test")
                .makerEmail("test")
                .build();
    }

    @Test
    @DisplayName("[성공] 메이커 회원 가입 테스트")
    void makerSaveTest() throws Exception {
        //given
        MakerCreateRequestDTO dto = MakerCreateRequestDTO.builder()
                .makerName(maker.getMakerName())
                .makerBrand(maker.getMakerBrand())
                .makerEmail(maker.getMakerEmail())
                .build();

        String json = mapper.writeValueAsString(dto);

        //when & then
        mvc.perform(post("/makers/sign-up")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath(code, 0).exists())
                .andExpect(jsonPath(message, "성공했습니다.").exists());
    }

    @Test
    @DisplayName("[성공] 메이커 수정 테스트")
    void makerUpdateTest() throws Exception {
        //given
        MakerUpdateRequestDTO updateRequestDTO = MakerUpdateRequestDTO.builder()
                .makerName("update")
                .makerBrand("update")
                .makerEmail("update")
                .build();

        String json = mapper.writeValueAsString(updateRequestDTO);

        //when & then
        mvc.perform(put("/makers/" + 1L)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath(code, 0).exists())
                .andExpect(jsonPath(message, "성공했습니다.").exists());
    }

    @Test
    @DisplayName("[성공] 메이커 삭제 테스트")
    void makerDeleteTest() throws Exception {
        //when & then
        mvc.perform(delete("/makers/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath(code, 0).exists())
                .andExpect(jsonPath(message, "성공했습니다.").exists());
    }

    @Test
    @DisplayName("[실패] 잘못된 데이터로 인한 메이커 생성 실패 테스트")
    void invalidNameTest() throws Exception {
        //given
        MakerCreateRequestDTO dto = MakerCreateRequestDTO.builder()
                .makerName(" ")
                .makerBrand(maker.getMakerBrand())
                .makerEmail(maker.getMakerEmail())
                .build();

        String json = mapper.writeValueAsString(dto);

        //조두
        MockHttpServletRequestBuilder builder = post("/makers/sign-up")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();

        String message = Objects.requireNonNull(result.getResolvedException()).getMessage();
        System.out.println("message = " + message);

        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message).contains("이름은 최소 2자 이상입니다.");
    }

    @Test
    @DisplayName("[실패] 잘못된 데이터로 인한 메이커 생성 실패 테스트")
    void invalidBrandTest() throws Exception {
        //given
        MakerCreateRequestDTO dto = MakerCreateRequestDTO.builder()
                .makerName(maker.getMakerName())
                .makerBrand(" ")
                .makerEmail(maker.getMakerEmail())
                .build();

        String json = mapper.writeValueAsString(dto);

        //조두
        MockHttpServletRequestBuilder builder = post("/makers/sign-up")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();

        String message = Objects.requireNonNull(result.getResolvedException()).getMessage();
        System.out.println("message = " + message);

        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message).contains("브랜드를 입력해주세요.");
    }

    @Test
    @DisplayName("[실패] 잘못된 데이터로 인한 메이커 생성 실패 테스트")
    void invalidEmailTest() throws Exception {
        //given
        MakerCreateRequestDTO dto = MakerCreateRequestDTO.builder()
                .makerName(maker.getMakerName())
                .makerBrand(maker.getMakerBrand())
                .makerEmail("aaa")
                .build();

        String json = mapper.writeValueAsString(dto);

        //when & then
        MockHttpServletRequestBuilder builder = post("/makers/sign-up")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();

        String message = Objects.requireNonNull(result.getResolvedException()).getMessage();
        System.out.println("message = " + message);

        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message).contains("이메일 형식이 맞지 않습니다.");
    }


}







