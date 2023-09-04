package com.prgrms.wadiz.domain.maker.dto;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import lombok.Builder;

@Builder
public record MakerServiceDTO(
        String makerName,
        String makerBrand,
        String makerEmail
) {
    public static Maker toEntity(MakerServiceDTO dto) {
        return Maker.builder()
                .makerName(dto.makerName())
                .makerBrand(dto.makerBrand())
                .makerEmail(dto.makerEmail())
                .build();
    }

    public static MakerServiceDTO from(Maker findMaker) {
        return MakerServiceDTO.builder()
                .makerName(findMaker.getMakerName())
                .makerBrand(findMaker.getMakerBrand())
                .makerEmail(findMaker.getMakerEmail())
                .build();
    }
}
