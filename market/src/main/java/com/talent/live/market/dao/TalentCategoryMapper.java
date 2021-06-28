package com.talent.live.market.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.talent.live.market.model.TalentCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TalentCategoryMapper extends BaseMapper<TalentCategory> {

    int updateByPrimaryKeySelective(TalentCategory record);

    List<String> selectCategoryIdByParentId(String categoryId);
}