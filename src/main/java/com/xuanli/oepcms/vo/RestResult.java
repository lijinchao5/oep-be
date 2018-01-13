package com.xuanli.oepcms.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResult<T> {
	/**状态*/
    private int code;
    /**对应状态的消息*/
    private String message;
	/**具体业务数据*/
    private T result;
    
    public static <T> RestResult<T> ok(T result) {
        RestResult<T> okResult = new RestResult<T>();
        okResult.setResult(result);
        return okResult;
    }

    public static <T> RestResult<T> failed(int code, String message) {
        RestResult<T> failedResult = new RestResult<T>();
        failedResult.setCode(code);
        failedResult.setMessage(message);
        return failedResult;
    }

    public static <T> RestResult<T> failed(int code, String message, T result) {
        RestResult<T> failedResult = new RestResult<T>();
        failedResult.setCode(code);
        failedResult.setMessage(message);
        failedResult.setResult(result);
        return failedResult;
    }
}
