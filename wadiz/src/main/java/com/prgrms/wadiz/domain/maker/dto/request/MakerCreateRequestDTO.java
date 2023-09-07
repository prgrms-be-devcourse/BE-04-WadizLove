package com.prgrms.wadiz.domain.maker.dto.request;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
public record MakerCreateRequestDTO(
        @Size(
                min = 2,
                message = "이름은 최소 2자 이상입니다."
        )
        String makerName,

        @NotBlank(message = "브랜드를 입력해주세요.")
        String makerBrand,

        @Email(message = "이메일을 입렬해주세요.")
        String makerEmail
) {
}