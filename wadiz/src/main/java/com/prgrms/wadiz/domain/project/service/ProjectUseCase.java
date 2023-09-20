package com.prgrms.wadiz.domain.project.service;

import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingUpdateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.service.FundingService;
import com.prgrms.wadiz.domain.maker.dto.MakerServiceDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.service.MakerService;
import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostUpdateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.post.service.PostService;
import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.dto.response.PagingDTO;
import com.prgrms.wadiz.domain.project.dto.response.ProjectPageResponseDTO;
import com.prgrms.wadiz.domain.project.dto.response.ProjectResponseDTO;
import com.prgrms.wadiz.domain.project.dto.response.ProjectSummaryResponseDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.project.repository.ProjectRepository;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.domain.reward.service.RewardService;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;
@Slf4j
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
                .orElseThrow(() -> {
                    log.warn("Project {} is not found", projectId);

                    return new BaseException(ErrorCode.PROJECT_NOT_FOUND);
                });

        if (!project.getMaker().getMakerId().equals(makerId)) {
            throw new BaseException(ErrorCode.MAKER_NOT_FOUND);
        }

        // boolean으로 확인할 부분
        if (!postService.isPostExist(projectId) ||
                !fundingService.isFundingExist(projectId) ||
                !rewardService.isRewardsExist(projectId)
        ) {
            throw new BaseException(ErrorCode.UNKNOWN);
        }

        project.setUpProject();
    }

//    @Cacheable(value = "projects", key = "#projectId")
    @Transactional(readOnly = true)
    public ProjectResponseDTO getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    log.warn("Project {} is not found", projectId);

                    return new BaseException(ErrorCode.PROJECT_NOT_FOUND);
                });

        PostResponseDTO postServiceDTO = postService.getPostByProjectId(projectId);
        FundingResponseDTO fundingServiceDTO = fundingService.getFundingByProjectId(projectId);
        List<RewardResponseDTO> rewardServiceDTOs = rewardService.getRewardsByProjectId(projectId);

        Maker maker = project.getMaker();
        MakerResponseDTO makerResponseDTO = MakerResponseDTO.of(
                maker.getMakerName(),
                maker.getMakerEmail(),
                maker.getMakerBrand()
        );

        return ProjectResponseDTO.of(
                projectId,
                makerResponseDTO,
                postServiceDTO,
                fundingServiceDTO,
                rewardServiceDTOs
        );
    }

    /**
     * Funding CRUD 서비스
     */
    @Transactional
    public Long createFunding(
            Long projectId,
            FundingCreateRequestDTO fundingCreateRequestDTO
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    log.warn("Project {} is not found", projectId);

                    return new BaseException(ErrorCode.PROJECT_NOT_FOUND);
                });

        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.from(project);

        return fundingService.createFunding(projectServiceDTO, fundingCreateRequestDTO);
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
    public Long createPost(
            Long projectId,
            PostCreateRequestDTO postCreateRequestDTO
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    log.warn("Project {} is not found", projectId);

                    return new BaseException(ErrorCode.PROJECT_NOT_FOUND);
                });

        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.from(project);

        return postService.createPost(projectServiceDTO, postCreateRequestDTO);
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
    @Transactional
    public void updateReward(
            Long projectId,
            Long rewardId,
            RewardUpdateRequestDTO dto
    ) {
        rewardService.updateReward(projectId, rewardId, dto);
    }

    @Transactional
    public void deleteReward(
            Long projectId,
            Long rewardId
    ) {
        rewardService.deleteReward(projectId, rewardId);
    }

    @Transactional
    public Long createReward(
            Long projectId,
            RewardCreateRequestDTO rewardCreateRequestDTO
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    log.warn("Project {} is not found", projectId);

                    return new BaseException(ErrorCode.PROJECT_NOT_FOUND);
                });

        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.from(project);

        return rewardService.createReward(projectServiceDTO, rewardCreateRequestDTO);
    }

    @Transactional(readOnly = true)
    public RewardResponseDTO getReward(
            Long projectId,
            Long rewardId
    ) {
        return rewardService.getReward(projectId, rewardId);
    }


//    @Cacheable(value = "projects")
    @Transactional(readOnly = true)
    public ProjectSummaryResponseDTO getProjects(
            String cursorId,
            int size,
            ProjectSearchCondition searchCondition,
            String criterion
    ) {
        Sort sort = (criterion != null)
                ? Sort.by(Sort.Order.desc(criterion))
                : Sort.by(Sort.Order.desc("fundingParticipants"));

        PageRequest pageRequest = PageRequest.of(
                0,
                size,
                sort
        );
        List<PagingDTO> pagingRes = projectRepository.findAllByCondition(
                cursorId,
                searchCondition,
                pageRequest
        );

        List<String> gotCursorIds = pagingRes.stream()
                .map(pagingDTO -> {
                    return generateCursor(criterion, pagingDTO);
                })
                .toList();

        String nextCursor = gotCursorIds.size() == 0 ? null : gotCursorIds.get(gotCursorIds.size()-1);

        List<ProjectPageResponseDTO> projectPages = IntStream.range(0, pagingRes.size())
                .mapToObj(i -> {
                    PagingDTO pagingDTO = pagingRes.get(i);
                    String gotCursorId = gotCursorIds.get(i);
                    return ProjectPageResponseDTO.of(
                            gotCursorId,
                            pagingDTO.getProjectId(),
                            pagingDTO.getTitle(),
                            pagingDTO.getThumbNailImage(),
                            pagingDTO.getMakerBrand(),
                            Funding.calculateSuccessRate(
                                    pagingDTO.getFundingAmount(),
                                    pagingDTO.getTargetFundingAmount()
                            ),
                            pagingDTO.getFundingAmount()
                    );
                }).toList();

        return ProjectSummaryResponseDTO.of(
                projectPages,
                pagingRes.size(),
                nextCursor
        );
    }

    @Transactional
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    log.warn("project : {} is not found", projectId);

                    throw new BaseException(ErrorCode.PROJECT_NOT_FOUND);
                });

        if (!isProjectBeforeSetUp(project)) {
            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        rewardService.deleteRewardsByProjectId(projectId);
        postService.deletePost(projectId);
        fundingService.deleteFunding(projectId);

        projectRepository.deleteById(projectId);

    }

    private boolean isProjectBeforeSetUp(Project project) {
        return project.getProjectStatus() == ProjectStatus.READY;
    }

    private String generateCursor(String criterion,PagingDTO pagingDTO){
        if (criterion == null){
            return String.format("%012d",pagingDTO.getFundingParticipants())
                    +String.format("%08d",pagingDTO.getProjectId());
        }

        switch (criterion) {
            case "fundingAmount":// 펀딩 금액 순
                return String.format("%012d",pagingDTO.getFundingAmount())
                        +String.format("%08d",pagingDTO.getProjectId());

            case "fundingEndAt": // 마감 임박 순
                return pagingDTO.getFundingEndAt().toString()
                        .replace("T","")
                        .replace("-","")
                        .replace(":","")
                        +String.format("%08d",pagingDTO.getProjectId());
            case "modifiedAt": // 최신 순
                return pagingDTO.getModifiedAt().toString()
                        .replace("T","")
                        .replace("-","")
                        .replace(":","")
                        +String.format("%08d",pagingDTO.getProjectId());
            default:
                return String.format("%012d",pagingDTO.getFundingParticipants())
                        +String.format("%08d",pagingDTO.getProjectId());
        }

    }


}
