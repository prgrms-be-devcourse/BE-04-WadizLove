package com.prgrms.wadiz.global.util.resTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseTemplate {

    @Schema(description = "어플리케이션 자체 상태", example = "C0001")
    private String code;

    @Schema(description = "어플리케이션 자체 응답 메시지", example = "성공했습니다.")
    private String message;

}
