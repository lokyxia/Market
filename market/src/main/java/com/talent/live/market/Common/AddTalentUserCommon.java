package com.talent.live.market.Common;

import lombok.Data;

@Data
public class AddTalentUserCommon {
//    private String id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;
}
