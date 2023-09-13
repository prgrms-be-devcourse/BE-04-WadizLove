package com.prgrms.wadiz.domain.maker.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
public record MakerUpdateRequestDTO(
        @ApiParam(value = "메이커 이름")
        @Size(min = 2, message = "이름은 최소 2자 이상입니다.")
        String makerName,

        @ApiParam(value = "메이커 브랜드")
        @NotBlank(message = "브랜드를 입력해주세요.")
        String makerBrand,

        @ApiParam(value = "메이커 이메일")
        @Email(message = "이메일 형식이 맞지 않습니다.")
        String makerEmail
) {
}
