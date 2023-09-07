package com.prgrms.wadiz.domain.supporter.dto.response;

import lombok.Builder;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
public record SupporterResponseDTO(
        String supporterName,
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
}