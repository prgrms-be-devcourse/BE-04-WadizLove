package com.prgrms.wadiz.domain.project.dto.response;

import com.prgrms.wadiz.domain.project.entity.Project;
import lombok.Builder;

@Builder
public record ProjectSummaryResponseDTO(
        Long projectId,
        String title,
        String thumbNailImage,
        String makerBrand
) {
    public static ProjectSummaryResponseDTO from(Project project) {
        return ProjectSummaryResponseDTO.builder()
                .projectId(project.getProjectId())
                .build();
    }
}
