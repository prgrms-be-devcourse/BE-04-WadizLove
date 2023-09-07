package com.prgrms.wadiz.domain.reward.service;

import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
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
import java.util.Optional;

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

        return RewardResponseDTO.from(reward);
    }

    @Transactional
    public Long createReward(
            ProjectServiceDTO projectServiceDTO,
            RewardCreateRequestDTO dto
    ) {
        Project project = ProjectServiceDTO.toEntity(projectServiceDTO);

        Reward reward = Reward.builder()
                .project(project)
                .rewardName(dto.rewardName())
                .rewardDescription(dto.rewardDescription())
                .rewardQuantity(dto.rewardQuantity())
                .rewardPrice(dto.rewardPrice())
                .rewardType(dto.rewardType())
                .build();

        return rewardRepository.save(reward).getRewardId();
    }

    @Transactional
    public RewardResponseDTO updateReward(Long rewardId, RewardUpdateRequestDTO dto) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));

        reward.updateReward(dto.rewardName(),dto.rewardDescription(),dto.rewardQuantity(),dto.rewardPrice(),dto.rewardType(),dto.rewardStatus());

        Reward savedReward = rewardRepository.save(reward);

        return RewardResponseDTO.from(savedReward);
    }

    @Transactional
    public void deleteReward(Long rewardId) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));
        reward.deletedStatus();
        rewardRepository.deleteById(rewardId);
    }

    public boolean isRewardsExist(Long projectId) {
        Optional<List<Reward>> rewards = rewardRepository.findAllByProjectId(projectId);
        return rewards.isPresent();
    }

    public List<RewardResponseDTO> getRewardsByProjectId(Long projectId) {
        List<Reward> rewards = rewardRepository.findAllByProjectId(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));

        return rewards.stream().map(RewardResponseDTO::from).toList();
    }
}