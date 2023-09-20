package com.prgrms.wadiz.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record PostUpdateRequestDTO(

        @Schema(
                description = "포스트 제목",
                example = "초특급으로 기분 좋아지는 히스투아 히포"
        )
        @NotBlank(message = "제목은 비워둘 수 없습니다.")
        String postTitle,

        @Schema(
                description = "포스트 상세 설명",
                example = "매그모를 가진 아이폰 유저들은 통화할 때 긴장할 일도, 번거롭게 메모지를 꺼낼 일도 없습니다. 통화와 동시에 녹음도 시작되며 파일을 바로 확인하는 매그모 2세대를 만나보세요."
        )
        @NotBlank(message = "상세 설명은 비워둘 수 없습니다.")
        String postDescription,

        @Schema(
                description = "썸네일 이미지 URL",
                example = "https://i.namu.wiki/i/hello.png"
        )
        @Pattern(
                regexp = "^https?://.*\\.(?:png|jpg|jpeg|gif)$",
                message = "올바른 이미지 URL 형식이 아닙니다."
        )
        @NotBlank(message = "이미지를 입력해주세요")
        String postThumbNailImage,

        @Schema(
                description = "컨텐트 이미지 URL",
                example = "https://i.namu.wiki/i/hello.png"
        )
        @Pattern(
                regexp = "^https?://.*\\.(?:png|jpg|jpeg|gif)$",
                message = "올바른 이미지 URL 형식이 아닙니다."
        )
        @NotBlank(message = "이미지를 입력해주세요")
        String postContentImage
) {
}
