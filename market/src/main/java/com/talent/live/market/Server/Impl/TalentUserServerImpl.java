package com.talent.live.market.Server.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.talent.live.market.Common.*;
import com.talent.live.market.Controller.TalentUserController;
import com.talent.live.market.Server.TalentUserServer;
import com.talent.live.market.dao.TalentUserMapper;
import com.talent.live.market.model.TalentUser;
import com.talent.live.market.util.PageResult;
import com.talent.live.market.util.Result;
import com.talent.live.market.util.TokenCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.sql.Wrapper;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class TalentUserServerImpl implements TalentUserServer {


    @Autowired
    private TalentUserMapper talentUserMapper;

    @Override
    public void addTalentUser(TalentUser talentUser) {

        List<TalentUser> talentUsers = talentUserMapper.selectList(null);

    }

    @Override
    public List<TalentUser> findTalentUser() {
        List<TalentUser> talentUsers = talentUserMapper.selectList(null);
        return talentUsers;
    }

    @Override
    public Result login(LoginCommon loginCommon) {

        String pass = DigestUtils.md5DigestAsHex(loginCommon.getPassword().getBytes());

        QueryWrapper<TalentUser> talentUserQueryWrapper = new QueryWrapper<>();
        talentUserQueryWrapper.eq("username",loginCommon.getUserName()).eq("password",pass);
        TalentUser talentUser = talentUserMapper.selectOne(talentUserQueryWrapper);
//        talentUser.setPassword(null);
        return Result.succeed(talentUser,"????????????");
        }

    @Override
    public Result register(AddTalentUserCommon newuser){

        QueryWrapper<TalentUser> Wrapper = new QueryWrapper<>();
        QueryWrapper<TalentUser> Wrapper2 = new QueryWrapper<>();
        Wrapper.eq("email",newuser.getEmail());
        Wrapper2.eq("username",newuser.getUsername());

//        List<TalentUser> userList1 = talentUserMapper.selectList(Wrapper2);
//        List<TalentUser> userList = talentUserMapper.selectList(Wrapper);

        CheckValidCommon checkValidCommon = new CheckValidCommon();
        checkValidCommon.setType("email");
        checkValidCommon.setStr(newuser.getEmail());
        Result result = checkValid(checkValidCommon);
        if (result.getCode()!=0){
            return result;
        }
        checkValidCommon.setType("username");
        checkValidCommon.setStr(newuser.getUsername());
        Result result2 = checkValid(checkValidCommon);
        if (result2.getCode()!=0){
            return result2;
        }

        TalentUser user = new TalentUser();
        BeanUtils.copyProperties(newuser,user);
        user.setId(UUID.randomUUID().toString().replace("-",""));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        try {
            talentUserMapper.insert(user);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failed("????????????");
        }
        return Result.succeed("????????????");
    }

    @Override
    public PageResult findAllUserPage(FindUserPageCommon common) {

        PageResult<TalentUser> PageResult = new PageResult<>();
        Page<Object> object = PageHelper.startPage(common.getCurrentPage(), common.getPageSize());
        List<TalentUser> talentUsers = talentUserMapper.selectList(null);
        PageInfo<TalentUser> pageInfo = new PageInfo<>(talentUsers);


        PageResult.setData(talentUsers);
        PageResult.setCount(object.getTotal());
        PageResult.setCode(0);
        return PageResult;
    }

    @Override
    public Result checkValid(CheckValidCommon common) {
        if (common.getType()!=null && common.getType().trim().length()>0){
            if ("username".equals(common.getType())){
                QueryWrapper<TalentUser> Wrapper = new QueryWrapper<>();
                Wrapper.eq("username",common.getStr());
                List<TalentUser> userList = talentUserMapper.selectList(Wrapper);
                if (userList!=null && userList.size()>0){
                    return Result.failed("?????????????????????");
                }

            }else if("email".equals(common.getType())){
                QueryWrapper<TalentUser> Wrapper2 = new QueryWrapper<>();
                Wrapper2.eq("email",common.getStr());
                List<TalentUser> userList1 = talentUserMapper.selectList(Wrapper2);
                if (userList1.size()>0){
                    return Result.failed("??????????????????");
                }
            }else{
                return Result.failed("??????????????????");
            }
        }else {
            return Result.failed("??????????????????");
        }
        return Result.succeed("????????????");
    }

    @Override
    public Result GetQuestion(String username) {
    String s =talentUserMapper.GetQuestion(username);
//    s=s.substring(1,3);
    if (s!=null && ""!=s){
        return Result.succeed(s,"??????????????????");
    }
    return Result.failed("???????????????");
    }

    @Override
    public Result ForgetCheckAnswer(ForgetCheckAnswerCommon common) {
        int count = talentUserMapper.ForgetCheckAnswer(common);
        if (count==0){
            return Result.failed("????????????");
        }
        return Result.succeed(count,"????????????");
    }

    @Override
    public Result ForgetResetPassword(forget_reset_passwordCommon common) {
        String username = common.getUsername();
        String token = TokenCache.getKey("token_" + username);
        if (token==null){
            return Result.failed("token??????");
        }
        if (!token.equals(common.getForgettoken())){
            return Result.failed("???????????????");
        }

        String pass = DigestUtils.md5DigestAsHex(common.getPasswordNew().getBytes());
        common.setPasswordNew(pass);
        int count= talentUserMapper.updateUserPassword(common);
        if (count==0){
            return Result.failed("????????????");
        }
        TokenCache.setKey("token"+username,pass);
        return Result.succeed("????????????????????????????????????1???");
    }

    @Override
    public Result reset_password(ResetPasswordCommon common) {
        int count = talentUserMapper.ResetPassword(common);
        if(count>0){
            return Result.succeed("??????????????????");
        }
        return Result.failed("?????????????????????");
    }

    @Override
    public Result update_information(UpdateInformationCommon common) {
        String value = TokenCache.getKey("token_" + common.getUsername());
        if (value!=null && value.length()>0){
            UpdateWrapper<TalentUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("username",common.getUsername());
            updateWrapper.set("email",common.getEmail()).set("phone",common.getPhone()).set("question",common.getQuestion())
                    .set("answer",common.getAnswer());
            int count = talentUserMapper.update(null, updateWrapper);
            if (count>0){
                return Result.succeed("????????????");
            }else {
                return Result.failed("????????????");
            }
        }
        return Result.failed("??????????????????");
    }

    @Override
    public Result loginBack(LoginCommon common) {
        String pass = DigestUtils.md5DigestAsHex(common.getPassword().getBytes());

        QueryWrapper<TalentUser> talentUserQueryWrapper = new QueryWrapper<>();
        talentUserQueryWrapper.eq("username",common.getUserName()).eq("password",pass).eq("role",0);
        TalentUser talentUser = talentUserMapper.selectOne(talentUserQueryWrapper);
//        talentUser.setPassword(null);
        if (talentUser==null){
            return Result.failed("?????????????????????");
        }else {
            return Result.succeed(talentUser,"?????????????????????");
        }
    }

    @Override
    public PageResult<TalentUser> list(FindUserPageCommon common) {
        PageHelper.startPage(common.getCurrentPage(),common.getPageSize());
//        QueryWrapper<TalentUser> talentUserQueryWrapper = new QueryWrapper<>();
        List<TalentUser> userList = talentUserMapper.selectList(null);
        PageInfo<TalentUser> talentUserPageInfo = new PageInfo<>(userList);
        PageResult<TalentUser> pageResult = new PageResult<>();
        pageResult.setCount(talentUserPageInfo.getTotal());
        return PageResult.success(userList,"????????????",talentUserPageInfo.getTotal());
    }
}