package com.talent.live.market.Common;

import lombok.Data;

@Data
public class forget_reset_passwordCommon {
    private String username;
    private String passwordNew;
    private String forgettoken;
}
