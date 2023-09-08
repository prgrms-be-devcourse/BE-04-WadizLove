package com.prgrms.wadiz.domain.maker.dto.response;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import lombok.Builder;

@Builder
public record MakerResponseDTO(
        Long makerId,
        String makerName,
        String makerBrand,
        String makerEmail
) {

    public static MakerResponseDTO from(Maker maker) {
        return MakerResponseDTO.builder()
                .makerId(maker.getMakerId())
                .makerName(maker.getMakerName())
                .makerEmail(maker.getMakerEmail())
                .makerBrand(maker.getMakerBrand())
                .build();
    }

    public static MakerResponseDTO of(
            String makerName,
            String makerBrand,
            String makerEmail
    ) {
        return MakerResponseDTO.builder()
                .makerName(makerName)
                .makerBrand(makerBrand)
                .makerEmail(makerEmail)
                .build();
    }
}