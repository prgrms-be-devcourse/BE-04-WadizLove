package com.prgrms.wadiz.domain.maker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
public record MakerUpdateRequestDTO(
        @Schema(
                description = "메이커 이름",
                example = "alohaJune"
        )
        @Size(
                min = 2,
                message = "이름은 최소 2자 이상입니다."
        )
        String makerName,

        @Schema(
                description = "메이커 브랜드",
                example = "Lotte"
        )
        @NotBlank(message = "브랜드를 입력해주세요.")
        String makerBrand,

        @Schema(
                description = "메이커 이메일",
                example = "lotte@gmail.com"
        )
        @Email(message = "이메일 형식이 맞지 않습니다.")
        String makerEmail
) {
}
