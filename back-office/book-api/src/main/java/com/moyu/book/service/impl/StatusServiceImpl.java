package com.moyu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moyu.book.dao.mapper.StatusMapper;
import com.moyu.book.dao.pojo.Status;
import com.moyu.book.service.StatusService;
import com.moyu.book.vo.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusMapper statusMapper;

    @Override
    public Result findOrderByUserId(Long userId) {
        LambdaQueryWrapper<Status> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Status::getUserId,userId);
        List<Status> statusList = statusMapper.selectList(queryWrapper);
        return Result.success(statusList);
    }
}
