package com.prgrms.wadiz.domain.supporter.dto.response;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
public record SupporterResponseDTO(
        @ApiParam(value = "서포터 이름")
        String supporterName,

        @ApiParam(value = "서포터 이메일")
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