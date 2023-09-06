package com.prgrms.wadiz.domain.maker.dto.request;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
public record MakerUpdateRequestDTO(
        @NotBlank(message = "이름을 입력해주세요.")
        @Min(value = 2, message = "이름은 최소 2자 이상입니다.")
        String makerName,

        @NotBlank(message = "브랜드를 입력해주세요.")
        String makerBrand,

        @Email @NotBlank(message = "이메일을 입렬해주세요.")
        String makerEmail
) {
}
