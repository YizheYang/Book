package com.moyu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moyu.book.dao.mapper.LibraryMapper;
import com.moyu.book.dao.mapper.StatusMapper;
import com.moyu.book.dao.pojo.Library;
import com.moyu.book.dao.pojo.Status;
import com.moyu.book.service.StatusService;
import com.moyu.book.vo.OrderVo;
import com.moyu.book.vo.Result;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private LibraryMapper libraryMapper;

    @Override
    public Result findOrderByUserId(Long userId) {
        LambdaQueryWrapper<Status> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Status::getUserId,userId);
        queryWrapper.eq(Status::getStatus,true);
        List<OrderVo> orderVoList = new LinkedList<>();
        List<Status> statusList = statusMapper.selectList(queryWrapper);

        for (Status status : statusList){
            LambdaQueryWrapper<Library> libraryLambdaQueryWrapper = new LambdaQueryWrapper<>();
            libraryLambdaQueryWrapper.eq(Library::getStatusId,status.getStatusId());
            libraryLambdaQueryWrapper.select(Library::getFloor,Library::getArea,Library::getNumber);
            Library library = libraryMapper.selectOne(libraryLambdaQueryWrapper);
            OrderVo orderVo = new OrderVo();

            BeanUtils.copyProperties(library,orderVo);
            BeanUtils.copyProperties(status,orderVo);



            orderVoList.add(orderVo);
        }

        return Result.success(orderVoList);
    }
}
