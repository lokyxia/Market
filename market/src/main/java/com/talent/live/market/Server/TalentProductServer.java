package com.talent.live.market.Server;

import com.talent.live.market.Common.TalentProductCommon;
import com.talent.live.market.util.Result;

public interface TalentProductServer {

    

    Result addTalentProduct(TalentProductCommon talentProductCommon);

    Result findTalentProductByCategoryId(String categoryId);
}
