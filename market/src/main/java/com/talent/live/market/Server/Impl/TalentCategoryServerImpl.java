package com.talent.live.market.Server.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.talent.live.market.Common.BackCommon.AddCategoryCommon;
import com.talent.live.market.Common.BackCommon.SetCategoryNameCommon;
import com.talent.live.market.Common.TalentCategoryCommon;
import com.talent.live.market.Server.TalentCategoryServer;
import com.talent.live.market.dao.TalentCategoryMapper;
import com.talent.live.market.model.TalentCategory;
import com.talent.live.market.model.TalentUser;
import com.talent.live.market.util.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class TalentCategoryServerImpl implements TalentCategoryServer {

    @Autowired
    private TalentCategoryMapper CategoryMapper;

//    @Override
//    public Result addCategory(TalentCategoryCommon TCC) {
//        TalentCategory talentCategory = new TalentCategory();
//        BeanUtils.copyProperties(TCC,talentCategory);
//        talentCategory.setCreateTime(new Date());
//        talentCategory.setUpdateTime(new Date());
//        String id = UUID.randomUUID().toString().replace("-", "");
//        talentCategory.setId(id);
//        try {
//            CategoryMapper.insert(talentCategory);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return Result.failed("失败");
//        }
//        return Result.succeed("成功");
//    }

    @Override
    public Result getCategroy(String categoryId) {
        QueryWrapper<TalentCategory> talentCategoryQueryWrapper = new QueryWrapper<>();
        talentCategoryQueryWrapper.eq("parent_id",categoryId);
        List<TalentCategory> talentCategories = CategoryMapper.selectList(talentCategoryQueryWrapper);
        if (talentCategories.size()>0){
            return Result.succeed(talentCategories,"查询成功");
        }else {
            return Result.failed("查询失败");
        }
    }

    @Override
    public Result addCategory(AddCategoryCommon common) {
        TalentCategory talentCategory = new TalentCategory();
        BeanUtils.copyProperties(common,talentCategory);
        talentCategory.setId(UUID.randomUUID().toString().replace("-",""));
        talentCategory.setCreateTime(new Date());
        talentCategory.setUpdateTime(new Date());
        int count = CategoryMapper.insert(talentCategory);
        if (count>0){
            return Result.succeed("新增成功");
        }else {
            return Result.failed("新增失败");
        }
    }

    @Override
    public Result SetCategoryName(SetCategoryNameCommon common) {
        TalentCategory talentCategory = new TalentCategory();
        talentCategory.setId(common.getCategoryId());
        talentCategory.setName(common.getCategoryName());
        int count = CategoryMapper.updateByPrimaryKeySelective(talentCategory);
        if (count==0){
            return Result.succeed("更新品类成功");
        }
        return Result.failed("更新品类失败");
    }

    @Override
    public Result getDeepCategory(String categoryId) {
//        根据parent_id查询分类集合
        List<String> list = CategoryMapper.selectCategoryIdByParentId(categoryId);
//        QueryWrapper wrapper = new QueryWrapper<>();
        List<String> categoryList = new ArrayList<>();
//        遍历分类集合
        list.stream().forEach(s -> {
            categoryList.add(s);
            List<String> list1 = getCategoryIdMethod(s);
            if (list1!=null){
                list1.stream().forEach(s1 -> {
                    categoryList.add(s1);
                });
            }
        });

        return Result.succeed(categoryList,"查询成功");
    }

    public List getCategoryIdMethod(String id){
        List<String> stringList = CategoryMapper.selectCategoryIdByParentId(id);
        if (stringList!=null && stringList.size()>0){
            return stringList;
        }else {
            return null;
        }

    }
}
