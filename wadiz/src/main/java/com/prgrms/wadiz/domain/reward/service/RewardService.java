package com.prgrms.wadiz.domain.reward.service;

import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
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
import java.util.Objects;
import java.util.Optional;

@Service
public class RewardService {

    private final RewardRepository rewardRepository;

    public RewardService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @Transactional
    public RewardResponseDTO createReward(
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

        Reward savedReward = rewardRepository.save(reward);

        RewardResponseDTO rewardResponseDTO = RewardResponseDTO.from(savedReward);

        return rewardResponseDTO;
    }

    @Transactional
    public RewardResponseDTO updateReward(Long projectId, Long rewardId, RewardUpdateRequestDTO dto) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));

        if (!Objects.equals(reward.getProject().getProjectId(), projectId)) {
            throw new BaseException(ErrorCode.NOT_MATCH);
        }

        if(!isProjectBeforeSetUp(reward.getProject())){
            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        reward.updateReward(dto.rewardName(),dto.rewardDescription(),dto.rewardQuantity(),dto.rewardPrice(),dto.rewardType(),dto.rewardStatus());

        return RewardResponseDTO.from(reward);
    }

    private boolean isProjectBeforeSetUp(Project project) {
        return project.getProjectStatus() == ProjectStatus.READY;
    }

    @Transactional
    public void deleteReward(Long projectId, Long rewardId) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));

        if (!Objects.equals(reward.getProject().getProjectId(), projectId)) {
            throw new BaseException(ErrorCode.NOT_MATCH);
        }

        if(!isProjectBeforeSetUp(reward.getProject())){
            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        reward.deletedStatus();
    }

    public boolean isRewardsExist(Long projectId) {
        Optional<List<Reward>> rewards = rewardRepository.findByProjectId(projectId);
        return rewards.isPresent();
    }

    public List<RewardResponseDTO> getRewardsByProjectId(Long projectId) {
        List<Reward> rewards = rewardRepository.findByProjectId(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));

        return rewards.stream().map(RewardResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public RewardResponseDTO getReward(Long projectId, Long rewardId){
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new BaseException(ErrorCode.REWARD_NOT_FOUND));

        if (!Objects.equals(reward.getProject().getProjectId(), projectId)) {
            throw new BaseException(ErrorCode.NOT_MATCH);
        }

        return RewardResponseDTO.from(reward);
    }

}
