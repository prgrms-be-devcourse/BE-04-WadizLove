package com.prgrms.wadiz.domain.reward.service;

import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.reward.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class RewardService {

    private final RewardRepository rewardRepository;

    public RewardService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
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
                .rewardType(RewardType.valueOf(dto.rewardType()))
                .build();

        Reward savedReward = rewardRepository.save(reward);

        return savedReward.getRewardId();
    }

    @Transactional
    public RewardResponseDTO updateReward(Long projectId, Long rewardId, RewardUpdateRequestDTO dto) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> {
                    log.warn("Reward {} is not found", rewardId);

                    throw new BaseException(ErrorCode.REWARD_NOT_FOUND);
                });

        if (!Objects.equals(reward.getProject().getProjectId(), projectId)) {

            throw new BaseException(ErrorCode.NOT_MATCH);
        }

        if(!isProjectBeforeSetUp(reward.getProject())){

            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        reward.updateReward(dto.rewardName(),
                dto.rewardDescription(),
                dto.rewardQuantity(),
                dto.rewardPrice(),
                RewardType.valueOf(dto.rewardType()),
                RewardStatus.valueOf(dto.rewardStatus())
        );

        return RewardResponseDTO.from(reward);
    }

    private boolean isProjectBeforeSetUp(Project project) {

        return project.getProjectStatus() == ProjectStatus.READY;
    }

    @Transactional
    public void deleteReward(Long projectId, Long rewardId) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> {
                    log.warn("Reward {} is not found", rewardId);

                    throw new BaseException(ErrorCode.REWARD_NOT_FOUND);
                });

        if (!Objects.equals(reward.getProject().getProjectId(), projectId)) {

            throw new BaseException(ErrorCode.NOT_MATCH);
        }

        if(!isProjectBeforeSetUp(reward.getProject())){

            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        reward.deletedStatus();
    }

    public boolean isRewardsExist(Long projectId) {
        Optional<List<Reward>> rewards = rewardRepository.findAllByProjectId(projectId);

        return rewards.isPresent();
    }

    public List<RewardResponseDTO> getRewardsByProjectId(Long projectId) {
        List<Reward> rewards = rewardRepository.findAllByProjectId(projectId)
                .orElseThrow(() -> {
                    log.warn("Rewards for Project {} is not found", projectId);

                    throw new BaseException(ErrorCode.REWARD_NOT_FOUND);
                });

        return rewards.stream().map(RewardResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public RewardResponseDTO getReward(Long projectId, Long rewardId){
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> {
                    log.warn("Reward {} is not found", rewardId);

                    throw new BaseException(ErrorCode.REWARD_NOT_FOUND);
                });

        if (!Objects.equals(reward.getProject().getProjectId(), projectId)) {

            throw new BaseException(ErrorCode.NOT_MATCH);
        }

        return RewardResponseDTO.from(reward);
    }

}
