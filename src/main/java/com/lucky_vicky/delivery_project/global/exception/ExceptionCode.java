package com.lucky_vicky.delivery_project.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    EXIST_PRODUCT(409, "존재하는 상품입니다."),
    PRODUCT_NOT_FOUNT(409, "상품을 찾을 수 없습니다."),
    PRODUCT_IS_HIDDEN(409, "숨김 처리된 상품입니다.");




    private int status;
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
