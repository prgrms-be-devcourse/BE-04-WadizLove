package com.prgrms.wadiz.domain.supporter.dto.response;

import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record SupporterResponseDTO(
        @Schema(description = "서포터 이름")
        String supporterName,

        @Schema(description = "서포터 이메일")
        String supporterEmail
) {
    public static SupporterResponseDTO of(
            String supporterName,
            String supporterEmail
    ) {
        return SupporterResponseDTO.builder()
                .supporterName(supporterName)
                .supporterEmail(supporterEmail)
                .build();
    }

    public static SupporterResponseDTO from(Supporter supporter) {
        return SupporterResponseDTO.builder()
                .supporterName(supporter.getSupporterName())
                .supporterEmail(supporter.getSupporterEmail())
                .build();
    }
}