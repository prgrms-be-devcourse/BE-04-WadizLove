package com.prgrms.wadiz.domain.reward.service;

import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.reward.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @InjectMocks
    private RewardService rewardService;

    @Mock
    private RewardRepository rewardRepository;

    @Test
    @DisplayName("[성공] 리워드 생성 테스트")
    void rewardCreateTest() {
        //given
        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.builder().build();

        RewardCreateRequestDTO createRequestDTO = RewardCreateRequestDTO.builder()
                .rewardName("test")
                .rewardDescription("test")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.SINGLE)
                .rewardStatus(RewardStatus.IN_STOCK)
                .build();

        when(rewardRepository.save(any(Reward.class))).then(AdditionalAnswers.returnsFirstArg());

        //when
        Long rewardId = rewardService.createReward(projectServiceDTO, createRequestDTO);
        Optional<Reward> savedReward = rewardRepository.findById(rewardId);

        //then
        assertThat(savedReward).isNotNull();
    }

    @Test
    @DisplayName("[성공] 리워드 수정 테스트")
    void rewardUpdateTest() {
        Project readyProject = Project.builder()
                .projectId(1L)
                .build();

        Reward reward = Reward.builder()
                .rewardName("test")
                .project(readyProject)
                .rewardDescription("test")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.SINGLE)
                .build();

        when(rewardRepository.findById(reward.getRewardId())).thenReturn(Optional.of(reward));

        RewardUpdateRequestDTO updateRequestDTO = RewardUpdateRequestDTO.builder()
                .rewardName("update")
                .rewardDescription("update")
                .rewardQuantity(1000)
                .rewardPrice(1000)
                .rewardType(RewardType.SUPER_EARLY_BIRD)
                .rewardStatus(RewardStatus.OUT_OF_STOCK)
                .build();

        //when
        RewardResponseDTO updateReward = rewardService.updateReward(
                1L,
                reward.getRewardId(),
                updateRequestDTO
        );

        //then
        assertThat(updateReward.rewardName()).isEqualTo("update");
        assertThat(updateReward.rewardDescription()).isEqualTo("update");
        assertThat(updateReward.rewardQuantity()).isEqualTo(1000);
        assertThat(updateReward.rewardPrice()).isEqualTo(1000);
        assertThat(updateReward.rewardType()).isEqualTo(RewardType.SUPER_EARLY_BIRD);
        assertThat(updateReward.rewardStatus()).isEqualTo(RewardStatus.OUT_OF_STOCK);
    }

    @Test
    @DisplayName("[성공] 리워드 soft-delete 테스트")
    void deleteRewardTest() {
        //given
        Project readyProject = Project.builder()
                .projectId(1L)
                .build();

        Reward reward = Reward.builder()
                .rewardName("test")
                .project(readyProject)
                .rewardDescription("test")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.SINGLE)
                .build();

        when(rewardRepository.findById(reward.getRewardId())).thenReturn(Optional.of(reward));

        //when
        rewardService.deleteReward(readyProject.getProjectId(),reward.getRewardId());
        Optional<Reward> findReward = rewardRepository.findById(reward.getRewardId());

        //then
        assertThat(findReward.get().getActivated()).isEqualTo(Boolean.FALSE);
    }
}