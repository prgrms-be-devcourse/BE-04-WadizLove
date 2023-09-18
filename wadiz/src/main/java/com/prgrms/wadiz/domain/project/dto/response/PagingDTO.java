package com.prgrms.wadiz.domain.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PagingDTO {
    private Long projectId;
    private String title;
    private String thumbNailImage;
    private String makerBrand;
    private Integer targetFundingAmount;
    private Integer fundingAmount;

    @Builder
    @QueryProjection
    public PagingDTO(
            Long projectId,
            String title,
            String thumbNailImage,
            String makerBrand,
            Integer targetFundingAmount,
            Integer fundingAmount
    ){
        this.projectId =projectId;
        this.title = title;
        this.thumbNailImage = thumbNailImage;
        this.makerBrand = makerBrand;
        this.targetFundingAmount = targetFundingAmount;
        this.fundingAmount = fundingAmount;
    }

}
