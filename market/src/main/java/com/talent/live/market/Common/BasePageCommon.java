package com.talent.live.market.Common;

import lombok.Data;

@Data
public class BasePageCommon {
    //    当前页
    private int currentPage=1;
    //    每页大小
    private int pageSize=10;
}
