package com.moyu.book.service;

import com.moyu.book.vo.Result;
import com.moyu.book.vo.params.ReserveParam;
import com.moyu.book.vo.params.UnsubscribeParam;

public interface ReserveService {
    Result reserve(ReserveParam reserveParam);

    Result unsubscribe(UnsubscribeParam unsubscribeParam);
}
