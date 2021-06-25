package com.talent.live.market.Controller;

import com.talent.live.market.Common.TalentProductCommon;
import com.talent.live.market.Server.TalentProductServer;
import com.talent.live.market.model.TalentProduct;
import com.talent.live.market.util.PageResult;
import com.talent.live.market.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/talentproduct")
public class TalentProductController {

    @Autowired
    private TalentProductServer talentProductServer;

    @PostMapping("/addtalentproduct")
    public Result addTalentProduct(@RequestBody TalentProductCommon common){
        return talentProductServer.addTalentProduct(common);
    }

    @PostMapping("/findTalentProductByCategoryId")
    public Result findTalentProductByCategoryId(@RequestBody String categoryId){

        return talentProductServer.findTalentProductByCategoryId(categoryId);
    }





}
