package com.prgrms.wadiz.domain.maker.dto.request;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import lombok.Builder;

@Builder
public record MakerCreateRequestDTO(
        String makerName,
        String makerBrand,
        String makerEmail) {
    public Maker toEntity() {
        return Maker.builder()
                .makerName(makerName)
                .makerBrand(makerBrand)
                .makerEmail(makerEmail)
                .build();
    }
}
