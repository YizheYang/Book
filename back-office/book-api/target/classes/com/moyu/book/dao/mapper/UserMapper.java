package com.moyu.book.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyu.book.dao.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
