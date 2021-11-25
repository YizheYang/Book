package com.moyu.book.service;

import com.moyu.book.vo.Result;
import com.moyu.book.vo.params.ReserveParam;

public interface ReserveService {
    Result reserve(ReserveParam reserveParam);
}
