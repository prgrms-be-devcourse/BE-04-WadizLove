package com.prgrms.wadiz.domain.project.controller;

import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostUpdateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
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

    @PostMapping("/{makerId}/{projectId}")
    public ResponseEntity<ResponseTemplate> createProject(
            @PathVariable Long makerId,
            @PathVariable Long projectId
    ) {
        projectService.createProject(makerId, projectId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseTemplate> getProject(@PathVariable Long projectId) {
        ProjectResponseDTO projectResponseDTO = projectService.getProject(projectId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(projectResponseDTO));
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
