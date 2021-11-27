package com.moyu.book.service;

import com.moyu.book.dao.mapper.UserMapper;
import com.moyu.book.vo.Result;
import com.moyu.book.vo.params.LoginParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {
    Result findAll(LoginParam loginParam);

    Result exchangePassword(LoginParam loginParam);
}
