package com.prgrms.wadiz.domain.maker.dto.response;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import lombok.Builder;

@Builder
public record MakerResponseDTO(
        String makerName,
        String makerBrand,
        String makerEmail) {
    public Maker toEntity() {
        return Maker.builder()
                .makerName(makerName)
                .makerEmail(makerEmail)
                .build();
    }
}