package com.moyu.book.dao.pojo;

import java.io.Serializable;
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
    private Long userId;

    /**
     *
     */
    private Long statusId;
    private static final long serialVersionUID = 1L;
}