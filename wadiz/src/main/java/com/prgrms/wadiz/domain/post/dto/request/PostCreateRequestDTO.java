package com.prgrms.wadiz.domain.post.dto.request;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
public record PostCreateRequestDTO(
        @NotBlank(message = "제목은 비워둘 수 없습니다.")
        String postTitle,

        @NotBlank(message = "상세 설명은 비워둘 수 없습니다.")
        String postDescription,

        @Pattern(regexp = "^https?://.*\\.(?:png|jpg|jpeg|gif)$", message = "올바른 이미지 URL 형식이 아닙니다.")
        String postThumbNailImage,

        @Pattern(regexp = "^https?://.*\\.(?:png|jpg|jpeg|gif)$", message = "올바른 이미지 URL 형식이 아닙니다.")
        String postContentImage
) {
}
