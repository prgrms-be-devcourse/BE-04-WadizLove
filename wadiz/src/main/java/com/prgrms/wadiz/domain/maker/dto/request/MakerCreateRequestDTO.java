package com.prgrms.wadiz.domain.maker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
public record MakerCreateRequestDTO(
        @Schema(
                description = "메이커 이름",
                example = "heyJune"
        )
        @Size(
                min = 2,
                message = "이름은 최소 2자 이상입니다."
        )
        @Pattern(
                regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$",
                message = "올바른 이름 형식이 아닙니다."
        )
        @NotBlank(message = "이름을 입력해주세요")
        String makerName,

        @Schema(
                description = "메이커 브랜드",
                example = "NoBrand"
        )
        @Pattern(
                regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$",
                message = "올바른 브랜드 형식이 아닙니다."
        )
        @NotBlank(message = "브랜드를 입력해주세요.")
        String makerBrand,

        @Schema(
                description = "메이커 이메일",
                example = "nobrand@gmail.com"
        )
        @Email(message = "이메일 형식이 맞지 않습니다.")
        @NotBlank(message = "이메일을 입력해주세요")
        String makerEmail
) {
}