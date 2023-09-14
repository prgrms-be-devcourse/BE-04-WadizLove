package com.prgrms.wadiz.global.util.resTemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonCode {
    SUCCESS("C0001", "성공했습니다."),
    FAIL("C0002", "실패했습니다.");

    private String code;
    private String message;
}
