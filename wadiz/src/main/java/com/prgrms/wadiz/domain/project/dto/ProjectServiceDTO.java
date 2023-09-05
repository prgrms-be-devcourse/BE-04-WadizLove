package com.prgrms.wadiz.domain.project.dto;

import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.project.entity.Project;
import lombok.Builder;

@Builder
public record ProjectServiceDTO(
        Long projectId,
        ProjectStatus projectStatus
        // TODO: Maker에 대한 lazy loading을 어떻게 처리할 것인지 + 과연 모든 필드가 다 필요한 것인지 확인해보기
) {
    public static ProjectServiceDTO from(Project project) {
        return ProjectServiceDTO.builder()
                .projectId(project.getProjectId())
                .projectStatus(project.getProjectStatus())
                .build();
    }

    public static Project toEntity(ProjectServiceDTO projectServiceDTO) {
        return Project.builder().build();
    }
}
