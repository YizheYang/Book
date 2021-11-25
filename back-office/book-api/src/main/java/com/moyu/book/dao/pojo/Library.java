package com.moyu.book.dao.pojo;

import java.io.Serializable;
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
    private Long statusId;

    /**
     * 
     */
    private Long adminId;

    private static final long serialVersionUID = 1L;
}