package com.prgrms.wadiz.domain.project.controller;

import com.prgrms.wadiz.domain.project.dto.request.ProjectCreateRequestDTO;
import com.prgrms.wadiz.domain.project.service.ProjectService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ResponseTemplate> createProject(ProjectCreateRequestDTO projectCreateRequestDTO) {
        projectService.createProject(projectCreateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }
}
