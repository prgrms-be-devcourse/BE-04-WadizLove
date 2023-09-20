package com.prgrms.wadiz.domain.supporter.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
public record SupporterCreateRequestDTO(
        @Schema(
                description = "서포터 이름",
                example = "june"
        )
        @Size(
                min = 2,
                message = "이름은 최소 2자 이상입니다."
        )
        @Pattern(
                regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$",
                message = "올바른 이름 형식이 아닙니다."
        )
        @NotBlank(message = "서포터 이름을 입력해주세요")
        String supporterName,

        @Schema(
                description = "서포터 이메일",
                example = "june@gmail.com"
        )
        @NotBlank(message = "서포터 이메일을 입력해주세요")
        @Email(message = "이메일 형식이 맞지 않습니다.")
        String supporterEmail
) {
}
