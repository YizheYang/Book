package com.moyu.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moyu.book.dao.mapper.LibraryMapper;
import com.moyu.book.dao.mapper.StatusMapper;
import com.moyu.book.dao.mapper.UserMapper;
import com.moyu.book.dao.pojo.Library;
import com.moyu.book.dao.pojo.Status;
import com.moyu.book.dao.pojo.User;
import com.moyu.book.service.SeatService;
import com.moyu.book.vo.Result;
import com.moyu.book.vo.SeatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private LibraryMapper libraryMapper;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public Result findAll() {

        List<Library> libraries = libraryMapper.selectList(new LambdaQueryWrapper<>());
        List<SeatVo> seatVoList = new LinkedList<>();
        for (Library library : libraries){
            SeatVo seatVo = new SeatVo();
            copyLibrary(library, seatVo);
            /**
             * 根据library的status_id找到所有的订单
             */
            LambdaQueryWrapper<Status> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Status::getStatusId,library.getStatusId());
            List<Status> statusList = statusMapper.selectList(queryWrapper);
            seatVo.setStatusList(statusList);
            seatVoList.add(seatVo);
        }
        return Result.success(seatVoList);
    }



    private void copyLibrary(Library library, SeatVo seatVo) {
        seatVo.setId(library.getId());
        seatVo.setFloor(library.getFloor());
        seatVo.setNumber(library.getNumber());
        seatVo.setArea(library.getArea());
    }
}
