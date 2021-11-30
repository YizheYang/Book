package com.moyu.book.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.moyu.book.dao.pojo.Status;
import lombok.Data;

import java.util.List;

@Data
public class SeatVo {

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
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
