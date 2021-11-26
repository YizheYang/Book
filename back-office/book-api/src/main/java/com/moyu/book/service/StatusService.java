package com.moyu.book.service;

import com.moyu.book.vo.Result;

public interface StatusService {
    Result findOrderByUserId(Long userId);
}
