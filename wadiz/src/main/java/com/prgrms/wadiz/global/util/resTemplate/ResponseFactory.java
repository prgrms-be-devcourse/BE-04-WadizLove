package com.prgrms.wadiz.global.util.resTemplate;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseFactory {
    // 단일 결과 처리 메소드
    public static  <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);

        return result;
    }

    // 복수 결과 처리 메소드
    public static <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setData(list);
        setSuccessResult(result);

        return result;
    }

    // 결과'만' 반환 (성공)
    public static ResponseTemplate getSuccessResult() {
        ResponseTemplate result = new ResponseTemplate();
        setSuccessResult(result);

        return result;
    }

    // 결과'만' 반환 (실패)
    public static ResponseTemplate getFailResult(String code, String message) {
        ResponseTemplate result = new ResponseTemplate();
        setFailResult(result, code, message);

        return result;
    }

    // API 요청 성공 시 공통 응답 모델을 성공 데이터로 세팅
    private static void setSuccessResult(ResponseTemplate result) {
        result.setCode(CommonCode.SUCCESS.getCode());
        result.setMessage(CommonCode.SUCCESS.getMessage());
    }

    // API 요청 성공 시 공통 응답 모델을 실패 데이터로 세팅
    private static void setFailResult(ResponseTemplate result, String code, String message) {
        result.setCode(code);
        result.setMessage(message);
    }
}
