package com.prgrms.wadiz.domain.project.service;

import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.funding.service.FundingService;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.service.MakerService;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import com.prgrms.wadiz.domain.post.service.PostService;
import com.prgrms.wadiz.domain.project.dto.response.ProjectResponseDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.project.repository.ProjectRepository;
import com.prgrms.wadiz.domain.maker.repository.MakerRepository;
import com.prgrms.wadiz.domain.reward.service.RewardService;
import com.prgrms.wadiz.global.util.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final MakerService makerService;
    private final FundingService fundingService;
    private final PostService postService;
    private final RewardService rewardService;

    private final ProjectRepository projectRepository;
    private final MakerRepository makerRepository;

    @Transactional
    public ProjectResponseDTO startProject(Long makerId) {
        // 질문
        // 1. repository에 직접 접근해서 정보를 가져오은 것이 좋은가?
        // 2. makerservice에서 정보를 가져오는 작업을 위임해서 maker 정보를 가져온다.
        //    -> 그렇다면, makerservice 에서는 도메인을 반환해줘야 하는가, dto를 반환해서 여기서 변환을 해줘야하는가.

        Maker maker = makerRepository.findById(makerId)
                .orElseThrow(() -> new BaseException(ErrorCode.MAKER_NOT_FOUND));

        Project project = Project.builder()
                .maker(maker)
                .build();

        return ProjectResponseDTO.from(projectRepository.save(project).getProjectId());
    }

    @Transactional
    public void createProject(Long makerId, Long projectId) { // 질문 : makerId는 받는데, 1. 받는게 필요한지(필요하다고 생각중),
                                                              // 2. 그리고 그 maker정보가 project의 maker정보와 맞는지 검증이 필요한가.
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));

        /**
         * TODO: project의 maker, post, funding 필드가 null인지 검사하는 로직
         * TODO: project의 maker가 맞는지 검사하는 로직
         */

        project.setUpProject();
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_FOUND));

        MakerResponseDTO makerResponseDTO = makerService.getMaker(project.getMaker().getMakerId());
        PostResponseDTO postResponseDTO = postService.getPost(project.getProjectId());
        FundingResponseDTO fundingResponseDTO = fundingService.getFunding(project.getFunding().getFundingId());
        List<RewardResponseDTO> rewardResponseDTOS = rewardService.getRewards(project.getProjectId());

        return ProjectResponseDTO.of(makerResponseDTO, postResponseDTO, fundingResponseDTO, rewardResponseDTOS);
    }
}
