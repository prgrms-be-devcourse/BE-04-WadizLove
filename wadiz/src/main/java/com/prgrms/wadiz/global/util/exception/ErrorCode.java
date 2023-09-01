package com.prgrms.wadiz.global.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNKNOWN(-9999, "알 수 없는 오류가 발생했습니다."),
    FUNDING_NOT_FOUND(-1000, "펀딩 정보를 찾을 수 없습니다."),
    POST_NOT_FOUND(-2000, "게시글 정보를 찾을 수 없습니다.");

    private int code;
    private String errorMessage;
}
