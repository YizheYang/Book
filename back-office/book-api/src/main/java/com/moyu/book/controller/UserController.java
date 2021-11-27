package com.moyu.book.controller;

import com.moyu.book.service.UserService;
import com.moyu.book.vo.Result;
import com.moyu.book.vo.params.LoginParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("expassword")
    public Result expassword(@RequestBody LoginParam loginParam){
        return userService.exchangePassword(loginParam);
    }
}
