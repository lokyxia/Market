package com.talent.live.market.Controller;

import com.talent.live.market.Common.*;
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
@RequestMapping("/talentUser")
public class TalentUserController {

    @Autowired
    private TalentUserServer talentUserServer;

    @PostMapping("/login")
    public Result login(@RequestBody LoginCommon loginCommon, HttpSession session){
        Result result = talentUserServer.login(loginCommon);
        if (result.getCode()==0){
            String value = UUID.randomUUID().toString();
            TalentUser talentUser= (TalentUser) result.getData();
            session.setAttribute("currentSession",result.getData());
            TokenCache.setKey("token_"+talentUser.getUsername(),value);
        }
        System.out.println(TokenCache.getKey(loginCommon.getUserName()));
        return talentUserServer.login(loginCommon);
    }

    @PostMapping("/register")
    public Result register(@RequestBody AddTalentUserCommon newuser){
        return talentUserServer.register(newuser);
    }

    @PostMapping("/findAllUserPage")
    private PageResult findAllUserPage(@RequestBody FindUserPageCommon fupc){
        return talentUserServer.findAllUserPage(fupc);

    }

    @RequestMapping("/check_valid")
    public Result check_valid(CheckValidCommon common){
        return talentUserServer.checkValid(common);
    }

    @RequestMapping("/get_user_info")
    public Result get_user_info(HttpSession session){
        TalentUser user_info_session = (TalentUser) session.getAttribute("currentSession");
        if (user_info_session==null){
            return Result.failed("用户未登录，请重新登录");
        }
        return Result.succeed(user_info_session,"查询已登录用户信息成功");
    }

    @RequestMapping("/logout")
    public Result logout(HttpSession session){
        session.removeAttribute("currentSession");
        return Result.succeed("登出成功");

    }

    @PostMapping("/get_question")
    public Result get_question(@RequestBody String username){
        return talentUserServer.GetQuestion(username);
    }


    @PostMapping("/forget_check_answer")
    public Result forget_check_answer(@RequestBody ForgetCheckAnswerCommon common){
        return talentUserServer.ForgetCheckAnswer(common);
    }

    @RequestMapping("/forget_reset_password")
    public Result forget_reset_password(@RequestBody forget_reset_passwordCommon common){
        return talentUserServer.ForgetResetPassword(common);
    }

    @PostMapping("/reset_password")
    public Result reset_password(@RequestBody ResetPasswordCommon common,HttpSession session ){
        TalentUser talentUser =(TalentUser) session.getAttribute("currentSession");
        common.setId(talentUser.getId());
        return talentUserServer.reset_password(common);
    }

    @PostMapping("/update_information")
    public  Result update_information(@RequestBody UpdateInformationCommon common,HttpSession session){
        TalentUser talentUser =(TalentUser)session.getAttribute("currentSession");
        common.setUsername(talentUser.getUsername());
         return talentUserServer.update_information(common);
    }
}
