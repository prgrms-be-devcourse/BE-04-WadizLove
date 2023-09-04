package com.prgrms.wadiz.domain.project.controller;

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




}
