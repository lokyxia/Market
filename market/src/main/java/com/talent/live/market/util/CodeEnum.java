package com.talent.live.market.util;

/**
 * @author huangzhengwei
 * @desc
 */
public enum CodeEnum {
    SUCCESS(0),
    ERROR(-1);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
