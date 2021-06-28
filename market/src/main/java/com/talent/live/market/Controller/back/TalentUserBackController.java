package com.talent.live.market.Controller.back;

import com.talent.live.market.Common.FindUserPageCommon;
import com.talent.live.market.Common.LoginCommon;
import com.talent.live.market.Server.TalentUserServer;
import com.talent.live.market.model.TalentUser;
import com.talent.live.market.util.PageResult;
import com.talent.live.market.util.Result;
import com.talent.live.market.util.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/TalentUserBack")
public class TalentUserBackController {

    @Autowired
    private TalentUserServer talentUserServer;

    @PostMapping("/loginBack")
    public Result loginBack(@RequestBody LoginCommon common, HttpSession session){
        Result result = talentUserServer.loginBack(common);
        String token = UUID.randomUUID().toString();
        if (result.getCode()!=-1){
            TalentUser talentUser = (TalentUser) result.getData();
            TokenCache.setKey("token_"+talentUser.getUsername(),token);
            session.setAttribute("current_back_session",talentUser);
        }
        return result;
    }

    @PostMapping("/list")
    public PageResult<TalentUser> list(@RequestBody FindUserPageCommon common,HttpSession session){
        TalentUser talentUser = (TalentUser) session.getAttribute("current_back_session");
        if (talentUser==null){
            return PageResult.fail("请登录");
        }else if (talentUser.getRole()==0){
            return talentUserServer.list(common);
        }else {
            return PageResult.fail("你不是管理员");
        }
    }
}
