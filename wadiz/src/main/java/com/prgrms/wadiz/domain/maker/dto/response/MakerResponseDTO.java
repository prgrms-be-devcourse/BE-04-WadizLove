package com.prgrms.wadiz.domain.maker.dto.response;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MakerResponseDTO(
        @Schema(description = "메이커 아이디")
        Long makerId,

        @Schema(description = "메이커 이름")
        String makerName,

        @Schema(description = "메이커 브랜드")
        String makerBrand,

        @Schema(description = "메이커 이메일")
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