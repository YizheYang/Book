package com.moyu.book.controller;

import com.moyu.book.service.ReserveService;
import com.moyu.book.vo.Result;
import com.moyu.book.vo.params.ReserveParam;
import com.moyu.book.vo.params.UnsubscribeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    @PostMapping("reserve")
    public Result reserve(@RequestBody ReserveParam reserveParam){
        return  reserveService.reserve(reserveParam);
    }
    @PostMapping("unsubscribe")
    public Result unsubscribe(@RequestBody UnsubscribeParam unsubscribeParam){
        return  reserveService.unsubscribe(unsubscribeParam);
    }


}
