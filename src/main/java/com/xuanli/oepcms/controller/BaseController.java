package com.xuanli.oepcms.controller;

import com.xuanli.oepcms.vo.RestResult;

public abstract class BaseController {
    public <T> RestResult<T> ok(T result) {
        return RestResult.ok(result);
    }

    public <T> RestResult<T> failed(int code, String message, T result) {
        return RestResult.failed(code, message, result);
    }
}
