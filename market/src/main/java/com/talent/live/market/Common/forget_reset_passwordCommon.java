package com.talent.live.market.Common;

import lombok.Data;

@Data
public class forget_reset_passwordCommon {
    private String username;
    private String password;
    private String forgettoken;
}
