package com.prgrms.wadiz.domain.supporter.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Builder
public record SupporterUpdateRequestDTO(
        @Schema(description = "서포터 이름")
        @Size(min = 2, message = "이름은 최소 2자 이상입니다.")
        String supporterName,

        @Schema(description = "서포터 이메일")
        @Email(message = "이메일 형식이 맞지 않습니다.")
        String supporterEmail
) {
}
