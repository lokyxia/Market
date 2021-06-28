package com.talent.live.market.Controller.back;

import com.talent.live.market.Common.BackCommon.AddCategoryCommon;
import com.talent.live.market.Common.BackCommon.SetCategoryNameCommon;
import com.talent.live.market.Server.TalentCategoryServer;
import com.talent.live.market.model.TalentCategory;
import com.talent.live.market.model.TalentUser;
import com.talent.live.market.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/TalentCategory")
public class TalentCategoryBackController {

    @Autowired
    private TalentCategoryServer talentCategoryServer;


    @RequestMapping(method = RequestMethod.POST,value = "/get_category")
    public Result get_category(@RequestBody String categoryId, HttpSession session){
        Result result = this.validCheckLogin(session);
        if (result.getData()==null){
            return Result.failed("请登录");
        } else {
            return talentCategoryServer.getCategroy(categoryId);
        }
    }

    @RequestMapping(method = RequestMethod.POST,value = "/add_category")
    public Result addCategory(@RequestBody AddCategoryCommon common,HttpSession session){
        Result result = this.validCheckLogin(session);
        if (result.getData()==null){
            return Result.failed("请登录");
        }else {
            return talentCategoryServer.addCategory(common);
        }
    }

    @RequestMapping(method = RequestMethod.POST,value = "/set_category_name")
    public Result SetCategoryName(@RequestBody SetCategoryNameCommon common,HttpSession session){
        Result result = this.validCheckLogin(session);
        if (result.getData()==null){
            return result;
        }
        return talentCategoryServer.SetCategoryName(common);
    }


    @PostMapping("/get_Deep_Category")
    public Result getDeepCategory(@RequestBody String categoryId,HttpSession session){
        Result result = this.validCheckLogin(session);
        if (result.getData()==null){
            return result;
        }
        return talentCategoryServer.getDeepCategory(categoryId);

    }

    /*
    * 检测是否登录
    *
    * */
    public Result validCheckLogin(HttpSession session){
        TalentUser current_back_user = (TalentUser) session.getAttribute("current_back_session");
        if (current_back_user==null){
            return Result.failed("未登录");
        }
        if (current_back_user.getRole().equals(1)){
            return Result.failed("权限不足");
        }
        return Result.succeed(current_back_user,"登录成功");
    }
}
