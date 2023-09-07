package com.prgrms.wadiz.domain.supporter.dto.request;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
public record SupporterCreateRequestDTO(
        @Size(
                min = 2,
                message = "이름은 최소 2자 이상입니다."
        )
        String supporterName,

        @Email(message = "이메일을 입렬해주세요.")
        String supporterEmail
) {
}
