package com.prgrms.wadiz.domain.supporter.dto.request;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
public record SupporterUpdateRequestDTO(
        @NotBlank(message = "이름을 입력해주세요.")
        @Min(value = 2, message = "최소 2자 이상입니다.")
        String supporterName,

        @Email @NotBlank(message = "이메일 정보를 입력해 주세요")
        String supporterEmail
) {
}
