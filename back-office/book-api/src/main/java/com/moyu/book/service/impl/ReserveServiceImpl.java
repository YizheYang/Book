package com.moyu.book.service.impl;

import com.moyu.book.service.ReserveService;
import com.moyu.book.vo.Result;
import com.moyu.book.vo.params.ReserveParam;
import org.springframework.stereotype.Service;

@Service
public class ReserveServiceImpl implements ReserveService {
    @Override
    public Result reserve(ReserveParam reserveParam) {
        /**
         * 1、查看status当前时间段是否为空闲，成功则加入预定表，否则返回false+已被预定（预定座位id，预定人id，开始时间，结束时间）
         *   1、查询libraryByNumber获取status_id
         *   2、通过status_id获得该座位的状态
         *   3、比对时间是否符合，符合找到 user_id 并添加，返回success，否则返回false
         */
        Long libraryId = reserveParam.getLibraryId();
        Long userId = reserveParam.getUserId();
        Long sdate = reserveParam.getSdate();
        Long ddate = reserveParam.getDdate();
        return null;
    }
}
