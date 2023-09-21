package com.prgrms.wadiz.global.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNKNOWN("G0001", "알 수 없는 오류가 발생했습니다."),
    INVALID_ACCESS("G0002", "잘못된 접근잊니다."),
    DUPLICATED_EMAIL("G0003", "중복된 이메일이 이미 존재합니다."),
    DUPLICATED_NAME("G0004", "중복된 이름이 이미 존재합니다." ),
    INVALID_REQUEST("G0005","잘못된 요청입니다."),

    REWARD_NOT_FOUND("R0001", "리워드 정보를 찾을 수 없습니다."),
    STOCK_SETTING_ERROR("R0002", "재고는 양수여야 합니다."),
    NOT_MATCH("R0003", "리워드의 프로젝트 아이디에 매치할 수 없습니다."),

    FUNDING_NOT_FOUND("F0001", "펀딩 정보를 찾을 수 없습니다."),
    INVALID_FUNDING_DURATION("F0002", "펀딩 종료 시간이 펀딩 시작 시간보다 앞설 수 없습니다."),
    CANNOT_CREATE_FUNDING("F0003", "펀딩을 새로 생성할 수 없습니다."),

    MAKER_NOT_FOUND("M0001","메이커  정보를 찾을 수 없습니다."),

    SUPPORTER_NOT_FOUND("S0001","서포터 정보를 찾을 수 없습니다."),

    PROJECT_NOT_FOUND("P0001", "프로젝트 정보를 찾을 수 없습니다."),
    PROJECT_ACCESS_DENY("P0002", "프로젝트가 개설된 이후로는 접근할 수 없습니다."),

    POST_NOT_FOUND("B0001", "게시글 정보를 찾을 수 없습니다."),
    CANNOT_CREATE_POST("B0002", "게시글을 새로 생성할 수 없습니다."),

    ORDER_COUNT_ERROR("O0001", "주문 수량은 1개 이상이어야 합니다."),
    ORDER_NOT_FOUND("O0002","주문 정보를 찾을 수 없습니다." );

    private String code;
    private String errorMessage;
}
