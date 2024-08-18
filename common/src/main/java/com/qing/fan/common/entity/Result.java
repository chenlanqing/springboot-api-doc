package com.qing.fan.common.entity;

import lombok.Data;

/**
 * @author QingFan
 * @version 1.0.0
 * @date 2024年08月17日 22:57
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(0, "success", data);
    }
}
