package com.moyu.book.controller;

import com.moyu.book.service.SeatService;
import com.moyu.book.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public Result search(){
        return seatService.findAll();
    }

}
