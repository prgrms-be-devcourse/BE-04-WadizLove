package com.prgrms.wadiz.domain.project.dto;

import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.project.entity.Project;
import lombok.Builder;

@Builder
public record ProjectServiceDTO(
        Long projectId,
        ProjectStatus projectStatus
) {
    public static ProjectServiceDTO from(Project project) {
        return ProjectServiceDTO.builder()
                .projectId(project.getProjectId())
                .projectStatus(project.getProjectStatus())
                .build();
    }

    public static Project toEntity(ProjectServiceDTO projectServiceDTO) {
        return Project.builder()
                .projectId(projectServiceDTO.projectId())
                .build();
    }
}
