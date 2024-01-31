package com.prgrms.wadiz.domain.reward.service;

import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.Project;
import com.prgrms.wadiz.domain.reward.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
                .rewardType(String.valueOf(RewardType.SINGLE))
                .rewardStatus(String.valueOf(RewardStatus.IN_STOCK))
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
                .rewardType(String.valueOf(RewardType.SUPER_EARLY_BIRD))
                .rewardStatus(String.valueOf(RewardStatus.OUT_OF_STOCK))
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

    @Test
    @DisplayName("[성공] Project id로 리워드 목록을 검색한다.")
    void getRewardsByProjectId() {
        //given
        Project project = Project.builder()
                .projectId(1L)
                .build();

        Reward reward1 = Reward.builder()
                .rewardName("test1")
                .project(project)
                .rewardDescription("test1")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.SINGLE)
                .build();


        Reward reward2 = Reward.builder()
                .rewardName("test2")
                .project(project)
                .rewardDescription("test2")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.SINGLE)
                .build();


        Reward reward3 = Reward.builder()
                .rewardName("test3")
                .project(project)
                .rewardDescription("test3")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.SINGLE)
                .build();

        List<Reward> rewardList = new ArrayList<>();

        rewardList.add(reward1);
        rewardList.add(reward2);
        rewardList.add(reward3);

        when(rewardRepository.findAllByProject_ProjectId(1L)).thenReturn(Optional.of(rewardList));

        //when
        List<RewardResponseDTO> rewardsByProjectId = rewardService.getRewardsByProjectId(1L);

        //then
        assertThat(rewardsByProjectId.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("[실패] Project가 런칭된 이후 Reward를 삭제하려고 하면 예외가 발생한다.")
    void deleteRewardAfterProjectSetUp() {
        // given
        Project launchedProject = Project.builder()
                .projectId(1L)
                .build();

        launchedProject.setUpProject();

        Reward reward = Reward.builder()
                .rewardName("test")
                .project(launchedProject)
                .rewardDescription("test")
                .rewardQuantity(100)
                .rewardPrice(100)
                .rewardType(RewardType.SINGLE)
                .build();

        when(rewardRepository.findById(reward.getRewardId())).thenReturn(Optional.ofNullable(reward));

        //when & then
        assertThatThrownBy(() -> rewardService.deleteReward(1L, reward.getRewardId()))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PROJECT_ACCESS_DENY);
    }

    @Test
    @DisplayName("[실패] Project가 런칭된 이후 Reward를 수정하려고 하면 예외가 발생한다.")
    void updateRewardAfterProjectSetUp() {
        // given
        Project launchedProject = Project.builder()
                .projectId(1L)
                .build();

        launchedProject.setUpProject();

        Reward reward = Reward.builder()
                        .rewardName("test")
                        .project(launchedProject)
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
                .rewardType(String.valueOf(RewardType.SUPER_EARLY_BIRD))
                .rewardStatus(String.valueOf(RewardStatus.OUT_OF_STOCK))
                .build();

        // when & then
        assertThatThrownBy(() -> rewardService.updateReward(1L, reward.getRewardId(), updateRequestDTO))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PROJECT_ACCESS_DENY);
    }

}