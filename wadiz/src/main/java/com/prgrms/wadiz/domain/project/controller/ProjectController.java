package com.prgrms.wadiz.domain.project.controller;

import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostUpdateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingUpdateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.dto.response.ProjectResponseDTO;
import com.prgrms.wadiz.domain.project.dto.response.ProjectPageResponseDTO;
import com.prgrms.wadiz.domain.project.dto.response.ProjectSummaryResponseDTO;
import com.prgrms.wadiz.domain.project.service.ProjectUseCase;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectUseCase projectUseCase;

    @PostMapping("/maker/{makerId}")    // TODO: api 좀 더 명확히
    public ResponseEntity<ResponseTemplate> startProject(@PathVariable Long makerId) {
        ProjectResponseDTO projectResponseDTO = projectUseCase.startProject(makerId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(projectResponseDTO));
    }
    @PostMapping("/{projectId}/maker/{makerId}/launching")
    public ResponseEntity<ResponseTemplate> createProject(
            @PathVariable Long projectId,
            @PathVariable Long makerId
    ) {
        projectUseCase.createProject(projectId, makerId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseTemplate> getProject(@PathVariable Long projectId) {
        ProjectResponseDTO projectResponseDTO = projectUseCase.getProject(projectId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(projectResponseDTO));
    }

    @GetMapping
    public ResponseEntity<ResponseTemplate> getProjects(
            @RequestParam(required = false) Long cursorId,
            @RequestParam int size,
            @RequestParam ProjectSearchCondition searchCondition,
            @RequestParam(required = false) String criterion
            ) {
        ProjectSummaryResponseDTO projectSummaryRes = projectUseCase.getProjects(
                cursorId,
                size,
                searchCondition,
                criterion
        );

        return ResponseEntity.ok(ResponseFactory.getSingleResult(projectSummaryRes));
    }

    /**
     * Funding 정보 CURD
     */
    @PostMapping("/{projectId}/fundings/new")
    public ResponseEntity<ResponseTemplate> createFunding(
            @PathVariable Long projectId,
            @RequestBody @Valid FundingCreateRequestDTO fundingCreateRequestDTO
    ) {
        projectUseCase.createFunding(projectId, fundingCreateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @GetMapping("/{projectId}/fundings")
    public ResponseEntity<ResponseTemplate> getFunding(@PathVariable Long projectId) {
        FundingResponseDTO fundingResponseDTO = projectUseCase.getFunding(projectId);
      
        return ResponseEntity.ok(ResponseFactory.getSingleResult(fundingResponseDTO));
    }

    @PutMapping("/{projectId}/fundings")
    public ResponseEntity<ResponseTemplate> updateFunding(
            @PathVariable Long projectId,
            @RequestBody @Valid FundingUpdateRequestDTO fundingUpdateRequestDTO
    ) {
        projectUseCase.updateFunding(projectId, fundingUpdateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @DeleteMapping("/{projectId}/fundings")
    public ResponseEntity<ResponseTemplate> deleteFunding(@PathVariable Long projectId) {
        projectUseCase.deleteFunding(projectId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    /**
     * Post 정보 CURD
     */
    @PostMapping("/{projectId}/posts/new")
    public ResponseEntity<ResponseTemplate> createPost(
            @PathVariable Long projectId,
            @RequestBody @Valid PostCreateRequestDTO postCreateRequestDTO
    ) {
        projectUseCase.createPost(projectId, postCreateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @GetMapping("/{projectId}/posts")
    public ResponseEntity<ResponseTemplate> getPost(@PathVariable Long projectId) {
        PostResponseDTO postResponseDTO = projectUseCase.getPost(projectId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(postResponseDTO));
    }

    @PutMapping("/{projectId}/posts")
    public ResponseEntity<ResponseTemplate> updatePost(
            @PathVariable Long projectId,
            @RequestBody @Valid PostUpdateRequestDTO postUpdateRequestDTO
    ) {
        projectUseCase.updatePost(projectId, postUpdateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @DeleteMapping("/{projectId}/posts")
    public ResponseEntity<ResponseTemplate> deletePost(@PathVariable Long projectId) {
        projectUseCase.deletePost(projectId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    /**
     * Reward 정보 CURD
     */
    @PostMapping("/{projectId}/rewards")
    public ResponseEntity<ResponseTemplate> createReward(
            @PathVariable Long projectId,
            @RequestBody @Valid RewardCreateRequestDTO rewardCreateRequestDTO
    ) {
        RewardResponseDTO rewardResponseDTO = projectUseCase.createReward(projectId, rewardCreateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(rewardResponseDTO));
    }

    @GetMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> getReward(
            @PathVariable Long projectId,
            @PathVariable Long rewardId
    ){
        RewardResponseDTO rewardResponseDTO = projectUseCase.getReward(projectId, rewardId);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(rewardResponseDTO));
    }

    @PutMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> updateReward(
            @PathVariable Long projectId,
            @PathVariable Long rewardId,
            @RequestBody @Valid RewardUpdateRequestDTO dto
    ) {
        projectUseCase.updateReward(projectId,rewardId, dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @DeleteMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> deleteReward(
            @PathVariable Long projectId,
            @PathVariable Long rewardId) {
        projectUseCase.deleteReward(projectId, rewardId);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }
}
