package com.prgrms.wadiz.domain.reward.service;

import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.project.repository.ProjectRepository;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RewardServiceFacade {

    private final RewardRepository rewardRepository;

    private final ProjectRepository projectRepository;


    public RewardServiceFacade(RewardRepository rewardRepository, ProjectRepository projectRepository) {
        this.rewardRepository = rewardRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public RewardResponseDTO getReward(Long rewardId) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));

        return RewardResponseDTO.from(reward);

    }

    @Transactional
    public RewardResponseDTO createReward(Long projectId, RewardUpdateRequestDTO dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));

        Reward reward = Reward.builder()
                .rewardName(dto.rewardName())
                .rewardDescription(dto.rewardDescription())
                .rewardQuantity(dto.rewardQuantity())
                .rewardPrice(dto.rewardPrice())
                .rewardType(dto.rewardType())
                .build();

        reward.allocateProject(project);
        Reward savedReward = rewardRepository.save(reward);

        return RewardResponseDTO.from(savedReward);
    }

    @Transactional
    public RewardResponseDTO updateReward(Long rewardId, RewardCreateRequestDTO dto) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));

        reward.updateReward(dto.rewardName(),dto.rewardDescription(),dto.rewardQuantity(),dto.rewardPrice(),dto.rewardType(),dto.rewardStatus());

        Reward savedReward = rewardRepository.save(reward);

        return RewardResponseDTO.from(savedReward);
    }

    @Transactional
    public void deleteReward(Long rewardId) {
        rewardRepository.deleteById(rewardId);
    }

    public boolean isRewardsExist(Long projectId) {
        return false;
    }

    public List<RewardResponseDTO> getRewardsByProjectId(Long projectId) {
        return null;
    }
}
