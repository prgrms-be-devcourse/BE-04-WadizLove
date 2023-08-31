package com.prgrms.wadiz.global.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNKNOWN(-9999, "알 수 없는 오류가 발생했습니다."),
    CUSTOMER_NOT_FOUND(-1000, "조회하고자하는 고객이 존재하지 않습니다."),
    VOUCHER_NOT_FOUND(-2000, "조회하고자하는 바우처가 존재하지 않습니다.");

    private int code;
    private String errorMessage;
}
