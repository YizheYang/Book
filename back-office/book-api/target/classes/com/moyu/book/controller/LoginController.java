package com.moyu.book.controller;


import com.moyu.book.service.UserService;
import com.moyu.book.vo.Result;
import com.moyu.book.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        return userService.findAll(loginParam);
    }
}
