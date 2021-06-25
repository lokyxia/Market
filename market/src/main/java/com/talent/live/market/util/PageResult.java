package com.talent.live.market.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author huangzhengwei
 * @desc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageResult<T> {
    private static final Long EMPTY_DATA_COUNT = 0L;
    private Long count;
    /**
     * @see CodeEnum
     */
    private int code;
    private List<T> data;
    private String message;

    public static <T> PageResult<T> resultEmpty(String msg, int code) {
        PageResultBuilder<T> builder = PageResult.builder();
        return builder.data(Collections.emptyList())
                .code(code)
                .message(msg)
                .build();
    }

    public static <T> PageResult<T> success(Collection<T> data, String msg, Long count) {
        PageResultBuilder<T> builder = PageResult.builder();
        return builder
                .data(new ArrayList<>(data))
                .code(CodeEnum.SUCCESS.getCode())
                .message(msg)
                .count(count)
                .build();
    }

    public static <T> PageResult<T> successEmpty() {
        return success(Collections.emptyList(), "没有指定条件的数据", EMPTY_DATA_COUNT);
    }

    public static <T> PageResult<T> success(Collection<T> data, Long count) {
        return success(data, "SUCCESS", count);
    }

    public static <T> PageResult<T> fail(Collection<T> data, String msg, Long count) {
        PageResultBuilder<T> builder = PageResult.builder();
        return builder
                .data(new ArrayList<>(data))
                .code(CodeEnum.ERROR.getCode())
                .message(msg)
                .count(count)
                .build();
    }

    public static <T> PageResult<T> fail(String msg) {
        return fail(Collections.emptyList(), msg, EMPTY_DATA_COUNT);
    }

    public static <T> PageResult<T> successWith(Long count, Integer code,List<T> dataList, String msg) {
        return new PageResult<>( count,code, dataList, msg);
    }

    public static <T> PageResult<T> successWith(Long count, List<T> dataList, String msg) {
        return new PageResult<>( count,CodeEnum.SUCCESS.getCode(), dataList, msg);
    }
    public static <T> PageResult<T> successWith(Long count, List<T> dataList) {
        return new PageResult<>( count,CodeEnum.SUCCESS.getCode(), dataList, "");
    }

    public static <T> PageResult<T> successWithNoData() {
        return new PageResult<>( 0L,CodeEnum.SUCCESS.getCode(), null, "暂无数据");
    }

    public static <T> PageResult<T> failedWith(Long count, Integer code,List<T> dataList, String msg) {
        return new PageResult<>( count,code, dataList, msg);
    }
}
