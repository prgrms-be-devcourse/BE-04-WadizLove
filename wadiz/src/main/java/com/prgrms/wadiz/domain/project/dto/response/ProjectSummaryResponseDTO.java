package com.prgrms.wadiz.domain.project.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectSummaryResponseDTO(
        List<ProjectPageResponseDTO> contents,
        int numberOfElements,
        String nextCursor
) {
    public static ProjectSummaryResponseDTO of(
            List<ProjectPageResponseDTO> projectPages,
            int numberOfElements,
            String nextCursor
    ) {
        return ProjectSummaryResponseDTO.builder()
                .contents(projectPages)
                .numberOfElements(numberOfElements)
                .nextCursor(nextCursor)
                .build();
    }
}
