package com.talent.live.market.Server;

import com.talent.live.market.Common.BackCommon.AddCategoryCommon;
import com.talent.live.market.Common.BackCommon.SetCategoryNameCommon;
import com.talent.live.market.Common.TalentCategoryCommon;
import com.talent.live.market.model.TalentCategory;
import com.talent.live.market.util.Result;

public interface TalentCategoryServer {
//    Result addCategory(TalentCategoryCommon TCC);

     Result getCategroy(String categoryId);

    Result addCategory(AddCategoryCommon common);

    Result SetCategoryName(SetCategoryNameCommon common);

    Result getDeepCategory(String categoryId);
}

