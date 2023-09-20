package com.prgrms.wadiz.domain.project.dto.response;

import lombok.Builder;

@Builder
public record ProjectPageResponseDTO(
        String cursorId,
        Long projectId,
        String title,
        String thumbNailImage,
        String makerBrand,
        Integer fundingSuccessRate,
        Integer fundingAmount
) {
    public static ProjectPageResponseDTO of(
            String cursorId,
            Long projectId,
            String title,
            String thumbNailImage,
            String makerBrand,
            Integer fundingSuccessRate,
            Integer fundingAmount
    ) {
       return ProjectPageResponseDTO.builder()
               .cursorId(cursorId)
               .projectId(projectId)
               .title(title)
               .thumbNailImage(thumbNailImage)
               .makerBrand(makerBrand)
               .fundingSuccessRate(fundingSuccessRate)
               .fundingAmount(fundingAmount)
               .build();
    }
}
