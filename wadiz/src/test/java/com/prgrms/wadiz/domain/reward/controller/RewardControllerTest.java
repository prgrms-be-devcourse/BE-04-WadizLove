package com.prgrms.wadiz.domain.reward.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardController.class)
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
}