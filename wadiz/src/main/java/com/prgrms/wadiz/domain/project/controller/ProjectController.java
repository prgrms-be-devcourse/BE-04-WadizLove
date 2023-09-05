package com.prgrms.wadiz.domain.project.controller;

import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostUpdateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingUpdateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.project.dto.response.ProjectResponseDTO;
import com.prgrms.wadiz.domain.project.service.ProjectService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> startProject(@PathVariable Long makerId) {
        ProjectResponseDTO projectResponseDTO = projectService.startProject(makerId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(projectResponseDTO));
    }
    @PostMapping("/{projectId}/maker/{makerId}")
    public ResponseEntity<ResponseTemplate> createProject(
            @PathVariable Long projectId,
            @PathVariable Long makerId
    ) {
        projectService.createProject(projectId, makerId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseTemplate> getProject(@PathVariable Long projectId) {
        ProjectResponseDTO projectResponseDTO = projectService.getProject(projectId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(projectResponseDTO));
    }

    @PostMapping("/{projectId}/fundings/new")
    public ResponseEntity<ResponseTemplate> createFunding(
            @PathVariable Long projectId,
            @RequestBody FundingCreateRequestDTO fundingCreateRequestDTO
    ) {
        projectService.createFunding(projectId, fundingCreateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @GetMapping("/{projectId}/fundings")
    public ResponseEntity<ResponseTemplate> getFunding(@PathVariable Long projectId) {
        FundingResponseDTO fundingResponseDTO = projectService.getFunding(projectId);
      
        return ResponseEntity.ok(ResponseFactory.getSingleResult(fundingResponseDTO));
    }

    @PutMapping("/{projectId}/fundings")
    public ResponseEntity<ResponseTemplate> updateFunding(
            @PathVariable Long projectId,
            @RequestBody FundingUpdateRequestDTO fundingUpdateRequestDTO
    ) {
        projectService.updateFunding(projectId, fundingUpdateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @DeleteMapping("/{projectId}/fundings")
    public ResponseEntity<ResponseTemplate> deleteFunding(@PathVariable Long projectId) {
        projectService.deleteFunding(projectId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }
      
    @PostMapping("/{projectId}/posts/new")
    public ResponseEntity<ResponseTemplate> createPost(
            @PathVariable Long projectId,
            @RequestBody PostCreateRequestDTO postCreateRequestDTO
    ) {
        projectService.createPost(projectId, postCreateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @GetMapping("/{projectId}/posts")
    public ResponseEntity<ResponseTemplate> getPost(@PathVariable Long projectId) {
        PostResponseDTO postResponseDTO = projectService.getPost(projectId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(postResponseDTO));
    }

    @PutMapping("/{projectId}/posts")
    public ResponseEntity<ResponseTemplate> updatePost(
            @PathVariable Long projectId,
            @RequestBody PostUpdateRequestDTO postUpdateRequestDTO
    ) {
        projectService.updatePost(projectId, postUpdateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @DeleteMapping("/{projectId}/posts")
    public ResponseEntity<ResponseTemplate> deletePost(@PathVariable Long projectId) {
        projectService.deletePost(projectId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }
}
