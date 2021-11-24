package com.moyu.book.vo;

import com.moyu.book.dao.pojo.User;
import lombok.Data;

@Data
public class Seat {

    /**
     *
     */
    private Long id;
    /**
     *楼层
     */
    private Integer floor;
    /**
     *座位号
     */
    private String number;
    /**
     *状态
     * true表示预定
     * false表示空闲
     */
    private boolean status;
    /**
     *开始时间
     * 空闲时为null
     */
    private Long sdate;
    /**
     *结束时间
     *空闲时为null
     */
    private Long ddate;
    /**
     *预定者信息
     *空闲时为null
     */
    private User user;

}
