package com.prgrms.wadiz.domain.project.service;

import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.service.FundingService;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.service.MakerService;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.post.service.PostService;
import com.prgrms.wadiz.domain.project.dto.request.ProjectCreateRequestDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.project.repository.ProjectRepository;
import com.prgrms.wadiz.domain.reward.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final MakerService makerService;
    private final FundingService fundingService;
    private final PostService postService;
    private final RewardService rewardService;

    private final ProjectRepository projectRepository;

    @Transactional
    public void createProject(ProjectCreateRequestDTO projectCreateRequestDTO) {
        Funding funding = fundingService.createFunding(projectCreateRequestDTO.getFundingCreateRequestDTO());
        Post post = postService.createPost(projectCreateRequestDTO.getPostCreateRequestDTO());
        Maker maker = makerService.getMaker(projectCreateRequestDTO.getMakerId());

        Project project = Project.builder()
                .funding(funding)
                .post(post)
                .maker(maker)
                .build();

        rewardService.allocateProject(projectCreateRequestDTO.getRewardCreateRequestDTO(), project);

        projectRepository.save(project);
    }
}
