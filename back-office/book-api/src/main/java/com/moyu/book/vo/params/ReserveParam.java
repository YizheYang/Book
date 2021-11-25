package com.moyu.book.vo.params;

import lombok.Data;

@Data
public class ReserveParam {


    private Long libraryId;

    private Long userId;

    /**
     * 开始时间
     */
    private Long sdate;

    /**
     * 结束时间
     */
    private Long ddate;
}
