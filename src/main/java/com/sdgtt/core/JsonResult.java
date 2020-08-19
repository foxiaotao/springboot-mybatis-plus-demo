package com.sdgtt.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JsonResult<T> implements Serializable {

    private static final Long API_INVOKE_UNEXPECTED_RESULT_CODE = 2L;
    private static final Long SUCCESS_CODE = 0L;
    private static final Long ERROR_STATE_CODE = 1L;
    private static final Long SUCCESS_BUSSINESS_CODE = 0L;
    private static final Long ERROR_BUSSINESS_CODE = 1L;
    private static final long serialVersionUID = -1L;
    private static final String ZERO_FILL_TEMPLATE = "%04d";
    private String msg = "";
    // 0成功，1失败
    private String code = "0000";
    // 业务错误码
    private String businessCode = "0000";

    private T data = null;

    public JsonResult() {
    }


    public JsonResult(String msg, Long code, T data) {
        this.msg = msg;
        this.code = String.format(ZERO_FILL_TEMPLATE, code);
        this.data = data;
    }

    public JsonResult(String msg, Long code, T data, Long businessCode) {
        this.msg = msg;
        this.code = String.format(ZERO_FILL_TEMPLATE, code);
        this.data = data;
        this.businessCode = String.format(ZERO_FILL_TEMPLATE, businessCode);
    }

    public JsonResult(T data) {
        this.data = data;
    }


    public static <T> JsonResult buildSuccessResult(String msg, T data) {
        return new JsonResult(msg, SUCCESS_CODE, data, SUCCESS_BUSSINESS_CODE);
    }

    public static <T> JsonResult buildSuccessResult(String msg, T data, Long businessId) {
        return new JsonResult(msg, SUCCESS_CODE, data, businessId);
    }

    public static <T> JsonResult buildErrorStateResult(String msg, T data) {
        return new JsonResult(msg, ERROR_STATE_CODE, data, ERROR_BUSSINESS_CODE);
    }

    public static <T> JsonResult buildErrorStateResult(String msg, T data, Long businessId) {
        return new JsonResult(msg, ERROR_STATE_CODE, data, businessId);
    }

    public static JsonResult buildErrorStateResult(String msg, Long businessId) {
        return new JsonResult(msg, ERROR_STATE_CODE, null, businessId);
    }

    public static <T> JsonResult buildFatalErrorStateResult(String msg, T data, Long businessId) {
        return new JsonResult(msg, ERROR_STATE_CODE, data, businessId);
    }

    public static <T> JsonResult buildApiInvokeUnexpectedResult(String msg, T data) {
        return new JsonResult(msg, SUCCESS_CODE, data, API_INVOKE_UNEXPECTED_RESULT_CODE);
    }

    public static <T> JsonResult buildErrorStateResult(String msg) {
        return new JsonResult(msg, ERROR_STATE_CODE, null, ERROR_BUSSINESS_CODE);
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return "0000".equals(code) && "0000".equals(businessCode);
    }

}
