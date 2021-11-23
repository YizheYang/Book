package com.moyu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moyu.book.dao.mapper.UserMapper;
import com.moyu.book.dao.pojo.User;
import com.moyu.book.service.UserService;
import com.moyu.book.utils.JWTUtils;
import com.moyu.book.vo.ErrorCode;
import com.moyu.book.vo.Result;
import com.moyu.book.vo.params.LoginParam;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Result findAll(LoginParam loginParam) {
        final String account = loginParam.getAccount();
        final String password = loginParam.getPassword();

        //判空
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        User user = findUser(account,password);
        if (user == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        return Result.success(user);
    }

    private User findUser(String account, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,account);
        queryWrapper.eq(User::getPassword,password);
        queryWrapper.select(User::getId,User::getAccount,User::getName,User::getTimeId);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }
}
