package com.prgrms.wadiz.domain.reward.service;

import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.project.repository.ProjectRepository;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
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
                .orElseThrow(() -> new RuntimeException("id에 해당하는 reward가 존재하지 않습니다."));

        return Reward.toDTOForResponse(reward);
    }

    //목록 조회는 커서기반 페이지네이션 예정

    @Transactional
    public RewardResponseDTO createReward(Long projectId, RewardCreateRequestDTO dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("id에 해당하는 프로젝트가 없습니다."));

        Reward reward = dto.toEntity();

        reward.allocateProject(project);
        Reward savedReward = rewardRepository.save(reward);

        return Reward.toDTOForResponse(savedReward);
    }

    @Transactional
    public RewardResponseDTO updateReward(Long rewardId, RewardUpdateRequestDTO dto) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RuntimeException("id에 해당하는 리워드가 없습니다."));

        reward.modifyRewardName(dto.rewardName());
        reward.modifyRewardDescription(dto.rewardDescription());
        reward.modifyRewardQuantity(dto.rewardQuantity());
        reward.modifyRewardPrice(dto.rewardPrice());
        reward.modifyRewardType(dto.rewardType());
        reward.modifyRewardStatus(dto.rewardStatus());

        Reward modifiedReward = rewardRepository.save(reward);

        return Reward.toDTOForResponse(modifiedReward);
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
