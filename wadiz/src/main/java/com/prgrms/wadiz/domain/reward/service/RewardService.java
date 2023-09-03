package com.prgrms.wadiz.domain.reward.service;

import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.project.repository.ProjectRepository;
import com.prgrms.wadiz.domain.reward.dto.request.RewardRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RewardService {

    private final RewardRepository rewardRepository;

    private final ProjectRepository projectRepository;


    public RewardService(RewardRepository rewardRepository, ProjectRepository projectRepository) {
        this.rewardRepository = rewardRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public RewardResponseDTO getReward(Long rewardId) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));

        return RewardResponseDTO.of(
                reward.getRewardName(),
                reward.getRewardDescription(),
                reward.getRewardQuantity(),
                reward.getRewardPrice(),
                reward.getRewardType(),
                reward.getRewardStatus());
    }

    @Transactional
    public RewardResponseDTO createReward(Long projectId, RewardRequestDTO dto) {
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

        return RewardResponseDTO.of(
                savedReward.getRewardName(),
                savedReward.getRewardDescription(),
                savedReward.getRewardQuantity(),
                savedReward.getRewardPrice(),
                reward.getRewardType(),
                reward.getRewardStatus());
    }

    @Transactional
    public RewardResponseDTO updateReward(Long rewardId, RewardRequestDTO dto) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));

        reward.modifyRewardName(dto.rewardName());
        reward.modifyRewardDescription(dto.rewardDescription());
        reward.modifyRewardQuantity(dto.rewardQuantity());
        reward.modifyRewardPrice(dto.rewardPrice());
        reward.modifyRewardType(dto.rewardType());
        reward.modifyRewardStatus(dto.rewardStatus());

        Reward savedReward = rewardRepository.save(reward);

        return RewardResponseDTO.of(
                savedReward.getRewardName(),
                savedReward.getRewardDescription(),
                savedReward.getRewardQuantity(),
                savedReward.getRewardPrice(),
                reward.getRewardType(),
                reward.getRewardStatus());
    }

    @Transactional
    public void deleteReward(Long rewardId) {
        rewardRepository.deleteById(rewardId);
    }
}
