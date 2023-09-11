package com.prgrms.wadiz.domain.project.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectSummaryResponseDTO(
        List<ProjectPageResponseDTO> contents,
        int numberOfElements,
        Long nextCursor
) {
    public static ProjectSummaryResponseDTO of(
            List<ProjectPageResponseDTO> projectPages,
            int numberOfElements,
            Long nextCursor
    ) {
        return ProjectSummaryResponseDTO.builder()
                .contents(projectPages)
                .numberOfElements(numberOfElements)
                .nextCursor(nextCursor)
                .build();
    }
}
