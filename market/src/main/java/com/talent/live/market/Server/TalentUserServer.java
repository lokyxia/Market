package com.talent.live.market.Server;

import com.talent.live.market.Common.*;
import com.talent.live.market.model.TalentUser;
import com.talent.live.market.util.PageResult;
import com.talent.live.market.util.Result;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface TalentUserServer {

    void addTalentUser(TalentUser talentUser);

    List<TalentUser> findTalentUser();

    Result login(LoginCommon loginCommon);

    Result register(AddTalentUserCommon newuser);

    PageResult findAllUserPage(FindUserPageCommon fupc);

    Result checkValid(CheckValidCommon common);

    Result GetQuestion(String username);

    Result ForgetCheckAnswer(ForgetCheckAnswerCommon common);

    Result ForgetResetPassword(forget_reset_passwordCommon common);
}
