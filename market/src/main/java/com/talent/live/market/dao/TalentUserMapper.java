package com.talent.live.market.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.talent.live.market.Common.ForgetCheckAnswerCommon;
import com.talent.live.market.Common.forget_reset_passwordCommon;
import com.talent.live.market.model.TalentUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface TalentUserMapper extends BaseMapper<TalentUser> {


    Integer selectByUsername(String Username);

    String GetQuestion(String username);

    int ForgetCheckAnswer(ForgetCheckAnswerCommon common);

    int updateUserPassword(forget_reset_passwordCommon common);

//    Integer selectUserByPass(String psw,String Username);
}