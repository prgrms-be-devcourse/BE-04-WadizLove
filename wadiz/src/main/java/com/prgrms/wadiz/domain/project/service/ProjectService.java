package com.prgrms.wadiz.domain.project.service;

import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingUpdateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.service.FundingServiceFacade;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.service.MakerService;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import com.prgrms.wadiz.domain.post.service.PostServiceFacade;
import com.prgrms.wadiz.domain.project.dto.response.ProjectResponseDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.project.repository.ProjectRepository;
import com.prgrms.wadiz.domain.reward.service.RewardServiceFacade;
import com.prgrms.wadiz.global.util.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final MakerService makerService;
    private final FundingServiceFacade fundingServiceFacade;
    private final PostServiceFacade postServiceFacade;
    private final RewardServiceFacade rewardServiceFacade;

    private final ProjectRepository projectRepository;

    @Transactional
    public void createProject(Long projectId, Long makerId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));

        if (!project.getMaker().getMakerId().equals(makerId)) {
            throw new BaseException(ErrorCode.MAKER_NOT_FOUND);
        }

        // boolean으로 확인할 부분
        if (!postServiceFacade.isPostExist(projectId) ||
            !fundingServiceFacade.isFundingExist(projectId)||
            !rewardServiceFacade.isRewardsExist(projectId)
        ) {
            throw new BaseException(ErrorCode.UNKNOWN);
        }

        project.setUpProject();
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));

        PostResponseDTO postServiceDTO = postServiceFacade.getPostByProjectId(projectId);
        FundingResponseDTO fundingServiceDTO = fundingServiceFacade.getFundingByProjectId(projectId);
        List<RewardResponseDTO> rewardServiceDTOs = rewardServiceFacade.getRewardsByProjectId(projectId);

        Maker maker = project.getMaker();
        MakerResponseDTO makerResponseDTO = MakerResponseDTO.of(maker.getMakerName(), maker.getMakerEmail(), maker.getMakerBrand());

        return ProjectResponseDTO.of(projectId, makerResponseDTO, postServiceDTO, fundingServiceDTO, rewardServiceDTOs);
    }

    @Transactional
    public void createFunding(Long projectId, FundingCreateRequestDTO fundingCreateRequestDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));
        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.from(project);

        fundingServiceFacade.createFunding(projectServiceDTO, fundingCreateRequestDTO);
    }

    @Transactional(readOnly = true)
    public FundingResponseDTO getFunding(Long projectId) {
        return fundingServiceFacade.getFundingByProjectId(projectId);
    }

    @Transactional
    public void updateFunding(
            Long projectId,
            FundingUpdateRequestDTO fundingUpdateRequestDTO
    ) {
        fundingServiceFacade.updateFunding(projectId, fundingUpdateRequestDTO);
    }

    @Transactional
    public void deleteFunding(Long projectId) {
        fundingServiceFacade.deleteFunding(projectId);
    }
}
