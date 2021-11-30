package com.moyu.book.controller;

import com.moyu.book.service.SeatService;
import com.moyu.book.service.StatusService;
import com.moyu.book.service.UserService;
import com.moyu.book.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private StatusService statusService;

    @GetMapping
    public Result search(){
        return seatService.findAll();
    }

    @GetMapping("order/{UserId}")
    public Result searchOrder(@PathVariable Long UserId){
        return statusService.findOrderByUserId(UserId);
    }

}
