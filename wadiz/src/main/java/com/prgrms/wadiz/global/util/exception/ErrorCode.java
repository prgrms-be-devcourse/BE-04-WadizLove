package com.prgrms.wadiz.global.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNKNOWN(-9999, "알 수 없는 오류가 발생했습니다."),
    ORDER_COUNT_ERROR(-10001, "주문 수량은 1개 이상이어야 합니다."),
    STOCK_SETTING_ERROR(-10000, "재고는 양수여야 합니다.");


    private int code;
    private String errorMessage;
}
