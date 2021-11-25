package com.moyu.book.dao.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName onlinetime
 */
@Data
public class Onlinetime implements Serializable {
    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 次数
     */
    private Integer frequency;

    /**
     * 时长
     */
    private String time;

    private static final long serialVersionUID = 1L;
}