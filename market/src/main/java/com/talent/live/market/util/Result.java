package com.talent.live.market.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangzhengwei
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private static final long serialVersionUID = -3226890674692965458L;
    private T data;
    private Integer code = CodeEnum.SUCCESS.getCode();
    private String message;
    public static final Result SUCCESS_RESULT = Result.succeed("SUCCESS");

    public static <T> Result<T> succeed(String msg) {
        return succeedWith(null, CodeEnum.SUCCESS.getCode(),msg);
    }

    public static <T> Result<T> succeed(T model, String msg) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(),msg);
    }

    public static <T> Result<T> succeedData(T model) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), null);
    }

    public static <T> Result<T> succeedWith(T datas, Integer code,String msg) {
        return new Result<T>(datas, code, msg);
    }

    public static <T> Result<T> failed(String msg) {

        return failedWith(null, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> Result<T> failed(T model,String msg) {

        return failedWith(model, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> Result<T> failedWith(T datas, Integer code, String msg) {
        return new Result<T>( datas, code, msg);
    }


}
