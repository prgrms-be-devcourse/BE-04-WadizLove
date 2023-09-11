package com.prgrms.wadiz.domain.reward.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.service.RewardService;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
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

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RewardControllerTest {

    @MockBean
    private RewardService rewardService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private Reward reward;

    private final String code = "$.[?(@.code == '%s')]";

    private final String message = "$.[?(@.message == '%s')]";

    @BeforeEach
    void setUp() {
        reward = Reward.builder()
                .rewardName("test")
                .rewardDescription("test")
                .rewardQuantity(10)
                .rewardPrice(100)
                .rewardType(RewardType.SINGLE)
                .build();
    }

    @Test
    @DisplayName("[성공] 리워드 생성 컨트롤러 테스트")
    void rewardCreateTest() throws Exception {
        //given
        RewardCreateRequestDTO createRequestDTO = RewardCreateRequestDTO.builder()
                .rewardName(reward.getRewardName())
                .rewardDescription(reward.getRewardDescription())
                .rewardQuantity(reward.getRewardQuantity())
                .rewardPrice(reward.getRewardPrice())
                .rewardType(reward.getRewardType())
                .rewardStatus(reward.getRewardStatus())
                .build();

        String jsonReward = mapper.writeValueAsString(createRequestDTO);

        //when & then
        mvc.perform(post("/projects/" + 1L + "/rewards")
                        .content(jsonReward)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(code, 0).exists())
                .andExpect(jsonPath(message, "성공했습니다.").exists());
    }

    @Test
    @DisplayName("[성공] 리워드 수정 컨트롤러 테스트")
    void rewardModifyTest() throws Exception {
        //given
        RewardUpdateRequestDTO updateRequestDTO = RewardUpdateRequestDTO.builder()
                .rewardName("update")
                .rewardDescription("update")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.SUPER_EARLY_BIRD)
                .rewardStatus(RewardStatus.SUSPEND)
                .build();

        String rewardJson = mapper.writeValueAsString(updateRequestDTO);

        //when & then
        mvc.perform((put("/projects/" + 1L + "/rewards/" + 1L))
                        .content(rewardJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(code, 0).exists())
                .andExpect(jsonPath(message, "성공했습니다.").exists());
    }

    @Test
    @DisplayName("[성공] 리워드 삭제 컨트롤러 테스트")
    void rewardDeleteTest() throws Exception {
        //when & then
        mvc.perform((delete("/projects/" + 1L + "/rewards/" + 1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(code, 0).exists())
                .andExpect(jsonPath(message, "성공했습니다.").exists());
    }

    @Test
    @DisplayName("[성공] 잘못된 데이터로 인한 리워드 생성 실패 테스트")
    void invalidNameTest() throws Exception {
        //given
        RewardCreateRequestDTO dto = RewardCreateRequestDTO.builder()
                .rewardName(" ")
                .rewardDescription("update")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.SUPER_EARLY_BIRD)
                .rewardStatus(RewardStatus.SUSPEND)
                .build();

        String json = mapper.writeValueAsString(dto);

        //when & then
        MockHttpServletRequestBuilder builder = post("/projects/" + 1L + "/rewards")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();

        String message = Objects.requireNonNull(result.getResolvedException()).getMessage();
        System.out.println("message = " + message);

        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message).contains("리워드명을 입력해주세요.");
    }

    @Test
    @DisplayName("[성공] 잘못된 데이터로 인한 리워드 생성 실패 테스트")
    void invalidQuantityTest() throws Exception {
        //given
        RewardCreateRequestDTO dto = RewardCreateRequestDTO.builder()
                .rewardName("update")
                .rewardDescription("update")
                .rewardQuantity(0)
                .rewardPrice(100)
                .rewardType(RewardType.SUPER_EARLY_BIRD)
                .rewardStatus(RewardStatus.SUSPEND)
                .build();

        String json = mapper.writeValueAsString(dto);

        //when & then
        MockHttpServletRequestBuilder builder = post("/projects/" + 1L + "/rewards")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();

        String message = Objects.requireNonNull(result.getResolvedException()).getMessage();
        System.out.println("message = " + message);

        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message).contains("리워드 재고는 최소 1개 이상입니다.");
    }

    @Test
    @DisplayName("[성공] 잘못된 데이터로 인한 리워드 생성 실패 테스트")
    void invalidPriceTest() throws Exception {
        //given
        RewardCreateRequestDTO dto = RewardCreateRequestDTO.builder()
                .rewardName("update")
                .rewardDescription("update")
                .rewardQuantity(100)
                .rewardPrice(5)
                .rewardType(RewardType.SUPER_EARLY_BIRD)
                .rewardStatus(RewardStatus.SUSPEND)
                .build();

        String json = mapper.writeValueAsString(dto);

        //when & then
        MockHttpServletRequestBuilder builder = post("/projects/" + 1L + "/rewards")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();

        String message = Objects.requireNonNull(result.getResolvedException()).getMessage();
        System.out.println("message = " + message);

        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message).contains("리워드 가격은 10원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("[성공] 잘못된 데이터로 인한 리워드 생성 실패 테스트")
    void invalidTypeTest() throws Exception {
        //given
        RewardCreateRequestDTO dto = RewardCreateRequestDTO.builder()
                .rewardName("update")
                .rewardDescription("update")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.valueOf("UNKNOWN"))
                .rewardStatus(RewardStatus.SUSPEND)
                .build();

        String json = mapper.writeValueAsString(dto);

        //when & then
        MockHttpServletRequestBuilder builder = post("/projects/" + 1L + "/rewards")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();

        String message = Objects.requireNonNull(result.getResolvedException()).getMessage();
        System.out.println("message = " + message);

        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message).contains("해당하는 리워드 상태가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("[성공] 잘못된 데이터로 인한 리워드 생성 실패 테스트")
    void invalidStatusTest() throws Exception {
        //given
        RewardCreateRequestDTO dto = RewardCreateRequestDTO.builder()
                .rewardName("update")
                .rewardDescription("update")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.SINGLE)
                .rewardStatus(RewardStatus.valueOf("UNKNOWN"))
                .build();

        String json = mapper.writeValueAsString(dto);

        //when & then
        MockHttpServletRequestBuilder builder = post("/projects/" + 1L + "/rewards")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();

        String message = Objects.requireNonNull(result.getResolvedException()).getMessage();
        System.out.println("message = " + message);

        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message).contains("해당하는 리워드 타입이 존재하지 않습니다.");
    }
}