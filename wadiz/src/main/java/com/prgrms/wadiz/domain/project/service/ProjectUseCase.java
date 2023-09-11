package com.prgrms.wadiz.domain.project.service;

import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingUpdateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.service.FundingService;
import com.prgrms.wadiz.domain.maker.dto.MakerServiceDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.service.MakerService;
import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostUpdateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.dto.response.ProjectSummaryResponseDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.domain.reward.service.RewardService;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import com.prgrms.wadiz.domain.post.service.PostService;
import com.prgrms.wadiz.domain.project.dto.response.ProjectResponseDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.project.repository.ProjectRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectUseCase {
    private final MakerService makerService;
    private final FundingService fundingService;
    private final PostService postService;
    private final RewardService rewardService;

    private final ProjectRepository projectRepository;

    /**
     * Project 관련 로직
     */
    @Transactional
    public ProjectResponseDTO startProject(Long makerId) {
         MakerServiceDTO makerServiceDTO = makerService.getMakerDTO(makerId);
         Maker maker = MakerServiceDTO.toEntity(makerServiceDTO);

        Project project = Project.builder()
                .maker(maker)
                .build();

        return ProjectResponseDTO.of(projectRepository.save(project).getProjectId());
    }

    @Transactional
    public void createProject(Long projectId, Long makerId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));

        if (!project.getMaker().getMakerId().equals(makerId)) {
            throw new BaseException(ErrorCode.MAKER_NOT_FOUND);
        }

        // boolean으로 확인할 부분
        if (!postService.isPostExist(projectId) ||
            !fundingService.isFundingExist(projectId)||
            !rewardService.isRewardsExist(projectId)
        ) {
            throw new BaseException(ErrorCode.UNKNOWN);
        }

        project.setUpProject();
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));

        PostResponseDTO postServiceDTO = postService.getPostByProjectId(projectId);
        FundingResponseDTO fundingServiceDTO = fundingService.getFundingByProjectId(projectId);
        List<RewardResponseDTO> rewardServiceDTOs = rewardService.getRewardsByProjectId(projectId);

        Maker maker = project.getMaker();
        MakerResponseDTO makerResponseDTO = MakerResponseDTO.of(maker.getMakerName(), maker.getMakerEmail(), maker.getMakerBrand());

        return ProjectResponseDTO.of(projectId, makerResponseDTO, postServiceDTO, fundingServiceDTO, rewardServiceDTOs);
    }

    /**
     * Funding CRUD 서비스
     */
    @Transactional
    public void createFunding(
            Long projectId, 
            FundingCreateRequestDTO fundingCreateRequestDTO
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));
        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.from(project);
      
        fundingService.createFunding(projectServiceDTO, fundingCreateRequestDTO);
    }

    @Transactional(readOnly = true)
    public FundingResponseDTO getFunding(Long projectId) {
        return fundingService.getFundingByProjectId(projectId);
    }

    @Transactional
    public void updateFunding(
            Long projectId,
            FundingUpdateRequestDTO fundingUpdateRequestDTO
    ) {
        fundingService.updateFunding(projectId, fundingUpdateRequestDTO);
    }

    @Transactional
    public void deleteFunding(Long projectId) {
        fundingService.deleteFunding(projectId);
    }

    /**
     * Post CRUD Service
     */
    @Transactional
    public void createPost(
            Long projectId,
            PostCreateRequestDTO postCreateRequestDTO
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));
        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.from(project);

        postService.createPost(projectServiceDTO, postCreateRequestDTO);
    }

    @Transactional(readOnly = true)
    public PostResponseDTO getPost(Long projectId) {
        return postService.getPostByProjectId(projectId);
    }

    @Transactional
    public void updatePost(
            Long projectId,
            PostUpdateRequestDTO postUpdateRequestDTO
    ) {
        postService.updatePost(projectId, postUpdateRequestDTO);
    }

    @Transactional
    public void deletePost(Long projectId) {
        postService.deletePost(projectId);
    }

    /**
     * Reward Service 관련 로직
     */

    public void updateReward(
            Long projectId,
            Long rewardId,
            RewardUpdateRequestDTO dto
    ) {
        rewardService.updateReward(projectId, rewardId, dto);
    }

    public void deleteReward(
            Long projectId,
            Long rewardId
    ) {
        rewardService.deleteReward(projectId, rewardId);
    }

    @Transactional
    public RewardResponseDTO createReward(
            Long projectId,
            RewardCreateRequestDTO rewardCreateRequestDTO
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));

        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.from(project);

        return rewardService.createReward(projectServiceDTO, rewardCreateRequestDTO);
    }

    @Transactional(readOnly = true)
    public RewardResponseDTO getReward(
            Long projectId,
            Long rewardId
    ){
        return rewardService.getReward(projectId, rewardId);
    }

    @Transactional(readOnly = true)
    public Page<ProjectSummaryResponseDTO> getProjects(Long cursorId, int size) {
        return projectRepository.findAllByCondition(
                cursorId,
                ProjectSearchCondition.OPEN,
                PageRequest.of(0, size)
        );
    }
}
