package com.talent.live.market.Controller;

import com.talent.live.market.Common.TalentCategoryCommon;
import com.talent.live.market.Server.TalentCategoryServer;
import com.talent.live.market.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/talentcategory")
public class TalentCategoryController {

    @Autowired
    private TalentCategoryServer CategoryServer;

//    @PostMapping("/addCategory")
//    public Result addTalentCategory(@RequestBody TalentCategoryCommon common){
//        return CategoryServer.addCategory(common);
//    }


}
