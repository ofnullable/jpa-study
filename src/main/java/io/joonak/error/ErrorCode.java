package io.joonak.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // field 순서 변경 시 유의해야한다.
public enum ErrorCode {

    // Account
    ACCOUNT_NOT_FOUND("AE-01", "계정을 찾을 수 없습니다.", 404),
    EMAIL_DUPLICATION("AE-02", "이메일이 이미 존재합니다.", 400),
    PASSWORD_FAILED_EXCEEDED("AE-03", "비밀번호 실패 횟수가 초과하였습니다.", 400),

    // Delivery
    DELIVERY_NOT_FOUND("DE-01", "배송정보를 찾을 수 없습니다.", 404),
    DELIVERY_STATUS_EQUALS("DE-02", "같은 배송상태로 수정할 수 없습니다.", 400),
    DELIVERY_ALREADY_COMPLETED("DE-03", "이미 완료된 배송정보입니다.", 400),

    // Order
    ORDER_NOT_FOUND("OE-01", "주문정보를 찾을 수 없습니다.", 404),

    // Coupon
    COUPON_NOT_FOUND("CE-01", "쿠폰정보를 찾을 수 없습니다.", 404),

    // Validation
    INPUT_VALUE_INVALID("VE-01", "올바른 형식으로 입력해주세요.", 400),

    // Database
    KEY_DUPLICATION("DE-01", "중복된 키가 존재합니다.", 400);

    private final String code;
    private final String message;
    private final int status;

}
