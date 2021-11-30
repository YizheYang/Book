package com.moyu.book.dao.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName library
 */
@Data
public class Library implements Serializable {
    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 区号
     */
    private String area;

    /**
     * 座位号
     */
    private Integer number;

    /**
     * 座位状态id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long statusId;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long adminId;

    private static final long serialVersionUID = 1L;
}