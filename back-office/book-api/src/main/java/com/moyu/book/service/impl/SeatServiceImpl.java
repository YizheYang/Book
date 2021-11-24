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
import com.moyu.book.vo.Seat;
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
        List<Seat> seatList = new LinkedList<>();
        for (Library library : libraries){
            Seat seat = new Seat();
            copyLibrary(library,seat);
            Status status = statusMapper.selectById(library.getStatusId());
            copyStatus(status,seat);
            User user = userMapper.selectById(status.getUserId());
            user.setPassword(null);
            seat.setUser(user);
            seatList.add(seat);
        }
        return Result.success(seatList);
    }

    private void copyStatus(Status status, Seat seat) {
        seat.setStatus(status.getStatus());
        seat.setSdate(status.getSdate());
        seat.setDdate(status.getDdate());
    }

    private void copyLibrary(Library library, Seat seat) {
        seat.setId(library.getId());
        seat.setFloor(library.getFloor());
        seat.setNumber(library.getNumber());
    }
}
