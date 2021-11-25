package com.moyu.book.vo;

import com.moyu.book.dao.pojo.Status;
import com.moyu.book.dao.pojo.User;
import lombok.Data;

import java.util.List;

@Data
public class SeatVo {

    /**
     *
     */
    private Long id;
    /**
     *楼层
     */
    private Integer floor;
    /**
     * 区号
     */
    private String area;
    /**
     *座位号
     */
    private Integer number;

    /**
     * 预定的订单
     */
    private List<Status> statusList;

}
