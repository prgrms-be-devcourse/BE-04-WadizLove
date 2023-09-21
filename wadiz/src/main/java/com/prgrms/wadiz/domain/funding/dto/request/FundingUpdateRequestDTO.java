package com.prgrms.wadiz.domain.funding.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.global.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record FundingUpdateRequestDTO(
        @Schema(
                description = "펀당 모집 금액",
                example = "20000000"
        )
        @Min(
                value = 1,
                message = "펀딩 모집 금액은 양수만 허용됩니다."
        )
        @NotNull(message = "펀딩 모집 금액을 입력해주세요")
        Integer fundingTargetAmount,

        @Schema(
                description = "펀딩 시작 시점",
                example = "2024-09-25 13:51:00",
                type = "string"
        )
        @FutureOrPresent(message = "펀딩 시작 시점을 과거 시각으로 설정할 수 없습니다.")
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss",
                timezone = "Asia/Seoul"
        )
        @NotNull(message = "펀딩 시작 시간을 입력해주세요")
        LocalDateTime fundingStartAt,

        @Schema(
                description = "펀딩 종료 시점",
                example = "2024-09-27 13:51:00",
                type = "string"
        )
        @FutureOrPresent(message = "펀딩 종료 시점을 과거 시각으로 설정할 수 없습니다.")
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss",
                timezone = "Asia/Seoul"
        )
        @NotNull(message = "펀딩 종료 시간을 입력해주세요")
        LocalDateTime fundingEndAt,

        @Schema(description = "펀딩 카테고리", example = "FASHION")
        @ValidEnum(
                enumClass = FundingCategory.class,
                message = "존재하지 않는 카테고리 입니다.",
                ignoreCase = true
        )
        @NotBlank(message = "펀딩 카테고리를 입력해주세요")
        String fundingCategory
) {
}
