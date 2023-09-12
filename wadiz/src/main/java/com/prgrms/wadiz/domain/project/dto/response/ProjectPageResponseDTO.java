package com.prgrms.wadiz.domain.project.dto.response;

import com.prgrms.wadiz.domain.project.entity.Project;
import lombok.Builder;

@Builder
public record ProjectPageResponseDTO(
        Long projectId,
        String title,
        String thumbNailImage,
        String makerBrand,
        Integer fundingSuccessRate,
        Integer fundingAmount
) {
    public static ProjectPageResponseDTO of(
            Long projectId,
            String title,
            String thumbNailImage,
            String makerBrand,
            Integer fundingSuccessRate,
            Integer fundingAmount
    ) {
       return ProjectPageResponseDTO.builder()
               .projectId(projectId)
               .title(title)
               .thumbNailImage(thumbNailImage)
               .makerBrand(makerBrand)
               .fundingSuccessRate(fundingSuccessRate)
               .fundingAmount(fundingAmount)
               .build();
    }
}
