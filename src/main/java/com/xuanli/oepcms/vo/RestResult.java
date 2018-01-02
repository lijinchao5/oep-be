package com.xuanli.oepcms.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResult<T> {
    private int code;
    private String message;
    private T result;

    public static <T> RestResult<T> ok(T result) {
        RestResult<T> okResult = new RestResult<>();
        okResult.setResult(result);
        return okResult;
    }

    public static <T> RestResult<T> failed(int code, String message, T result) {
        RestResult<T> failedResult = new RestResult<>();
        failedResult.setCode(code);
        failedResult.setMessage(message);
        failedResult.setResult(result);
        return failedResult;
    }
}
