package com.prgrms.wadiz.domain.maker.dto.response;

import lombok.Builder;

@Builder
public record MakerResponseDTO(
        String makerName,
        String makerBrand,
        String makerEmail) {
    public static MakerResponseDTO of(
            String makerName,
            String makerBrand,
            String makerEmail) {
        return MakerResponseDTO.builder()
                .makerName(makerName)
                .makerBrand(makerBrand)
                .makerEmail(makerEmail)
                .build();
    }
}