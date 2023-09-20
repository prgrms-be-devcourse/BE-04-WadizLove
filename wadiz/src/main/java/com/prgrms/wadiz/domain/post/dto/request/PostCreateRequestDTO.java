package com.prgrms.wadiz.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
public record PostCreateRequestDTO(
        @Schema(
                description = "포스트 제목",
                example = "보기만해도 행복해지는 히스투아 히포"
        )
        @NotBlank(message = "제목은 비워둘 수 없습니다.")
        String postTitle,

        @Schema(
                description = "포스트 상세 설명",
                example = "국내 공식 신규 런칭! 흔하지 않은 유니크한 디자인에 까다로은 인증까지 완료! 보기만해도 행복해지는 히스투아 히포를 우리 아이의 단짝 친구로 만들어주세요:)"
        )
        @NotBlank(message = "상세 설명은 비워둘 수 없습니다.")
        String postDescription,

        @Schema(
                description = "썸네일 이미지 URL",
                example = "https://i.namu.wiki/i/admin.png"
        )
        @Pattern(
                regexp = "^https?://.*\\.(?:png|jpg|jpeg|gif)$",
                message = "올바른 이미지 URL 형식이 아닙니다."
        )
        @NotBlank(message = "이미지를 입력해주세요")
        String postThumbNailImage,

        @Schema(
                description = "컨텐트 이미지 URL",
                example = "https://i.namu.wiki/i/admin.png"
        )
        @Pattern(
                regexp = "^https?://.*\\.(?:png|jpg|jpeg|gif)$",
                message = "올바른 이미지 URL 형식이 아닙니다."
        )
        @NotBlank(message = "이미지를 입력해주세요")
        String postContentImage
) {
}
