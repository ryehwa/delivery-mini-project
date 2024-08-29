package com.lucky_vicky.delivery_project.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    EXIST_PRODUCT(403, "존재하는 상품입니다."),
    PRODUCT_NOT_FOUNT(404, "상품을 찾을 수 없습니다."),
    PRODUCT_IS_HIDDEN(403, "숨김 처리된 상품입니다."),
    ORDER_NOT_FOUNT(404, "주문을 찾을 수 없습니다."),
    STORE_NOT_FOUND(404,"가게를 찾을 수 없습니다."),
    USER_NOT_FOUND(404,"사용자를 찾을 수 없습니다."),
    ORDER_CANCEL_TIME_EXCEEDED(422,"주문 취소는 주문 후 5분 이내에만 가능합니다.");




    private int status;
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
