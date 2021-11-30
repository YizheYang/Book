package com.moyu.book.dao.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName status
 */
@Data
public class Status implements Serializable {
    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 座位表状态
     */
    private Boolean status;

    /**
     * 开始时间
     */
    private Long sdate;

    /**
     * 结束时间
     */
    private Long ddate;

    /**
     * 申请者id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long statusId;
    private static final long serialVersionUID = 1L;
}