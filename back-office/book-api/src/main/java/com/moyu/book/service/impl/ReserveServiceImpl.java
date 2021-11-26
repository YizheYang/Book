package com.moyu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moyu.book.dao.mapper.LibraryMapper;
import com.moyu.book.dao.mapper.StatusMapper;
import com.moyu.book.dao.mapper.UserMapper;
import com.moyu.book.dao.pojo.Status;
import com.moyu.book.service.ReserveService;
import com.moyu.book.vo.ErrorCode;
import com.moyu.book.vo.Result;
import com.moyu.book.vo.params.ReserveParam;
import com.moyu.book.vo.params.UnsubscribeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private LibraryMapper libraryMapper;

    @Autowired
    private StatusMapper statusMapper;


    @Override
    public Result reserve(ReserveParam reserveParam) {
        /**
         * 1、查看status当前时间段是否为空闲，成功则加入预定表，否则返回false+已被预定（预定座位id，预定人id，开始时间，结束时间）
         *   1、查询libraryById获取status_id
         *   2、通过status_id获得该座位的状态
         *   3、比对时间是否符合，符合找到 user_id 并添加，返回success，否则返回false
         */
        Long libraryId = reserveParam.getLibraryId();
        Long userId = reserveParam.getUserId();
        Long sdate = reserveParam.getSdate();
        Long ddate = reserveParam.getDdate();

        Status status = new Status();
        Long statusId = libraryMapper.selectById(libraryId).getStatusId();

        status.setStatus(true);
        status.setStatusId(statusId);
        status.setSdate(sdate);
        status.setDdate(ddate);
        status.setUserId(userId);



        int insert = statusMapper.insert(status);
        if (insert == 0)
            return Result.fail(ErrorCode.FAIL_RESERVE.getCode(), ErrorCode.NO_LOGIN.getMsg());
        return Result.success(null);
    }

    @Override
    public Result unsubscribe(UnsubscribeParam unsubscribeParam) {

        Long orderId = unsubscribeParam.getOrderId();
        Long userId = unsubscribeParam.getUserId();
        LambdaQueryWrapper<Status> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Status::getId,orderId);
        queryWrapper.eq(Status::getUserId,userId);
        Status statusUpdate = new Status();
        statusUpdate.setStatus(false);
        int update = statusMapper.update(statusUpdate, queryWrapper);
        if (update!=0)
            return Result.success(update);
        return Result.fail(90004,"找不到该订单");
    }
}
