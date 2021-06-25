package com.talent.live.market.Server.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.talent.live.market.Common.TalentProductCommon;
import com.talent.live.market.Server.TalentProductServer;
import com.talent.live.market.dao.TalentProductMapper;
import com.talent.live.market.model.TalentProduct;
import com.talent.live.market.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TalentProductServerImpl implements TalentProductServer {

    @Autowired
    private TalentProductMapper talentProductMapper;

    @Override
    public Result addTalentProduct(TalentProductCommon Common) {
        TalentProduct talentProduct = new TalentProduct();
        BeanUtils.copyProperties(Common,talentProduct);
        talentProduct.setUpdateTime(new Date());
        talentProduct.setCreateTime(new Date());
        try {
            talentProductMapper.insert(talentProduct);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.failed("失败");
        }
        return Result.succeed("" +
                "成功");
    }

    @Override
    public Result findTalentProductByCategoryId(String categoryId) {
        categoryId=categoryId.substring(1,33);
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("category_id",categoryId);
        List<TalentProduct> Productlist = talentProductMapper.selectList(wrapper);
        return Result.succeed(Productlist,"成功");
    }
}
