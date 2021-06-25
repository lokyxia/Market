package com.talent.live.market.Server.Impl;

import com.talent.live.market.Common.TalentCategoryCommon;
import com.talent.live.market.Server.TalentCategoryServer;
import com.talent.live.market.dao.TalentCategoryMapper;
import com.talent.live.market.model.TalentCategory;
import com.talent.live.market.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TalentCategoryServerImpl implements TalentCategoryServer {

    @Autowired
    private TalentCategoryMapper CategoryMapper;

    @Override
    public Result addCategory(TalentCategoryCommon TCC) {
        TalentCategory talentCategory = new TalentCategory();
        BeanUtils.copyProperties(TCC,talentCategory);
        talentCategory.setCreateTime(new Date());
        talentCategory.setUpdateTime(new Date());
        String id = UUID.randomUUID().toString().replace("-", "");
        talentCategory.setId(id);
        try {
            CategoryMapper.insert(talentCategory);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.failed("失败");
        }
        return Result.succeed("成功");
    }
}
