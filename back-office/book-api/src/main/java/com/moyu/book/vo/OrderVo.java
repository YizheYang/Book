package com.moyu.book.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class OrderVo {

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
     * 开始时间
     */
    private Long sdate;

    /**
     * 结束时间
     */
    private Long ddate;



}
