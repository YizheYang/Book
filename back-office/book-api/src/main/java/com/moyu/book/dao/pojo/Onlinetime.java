package com.moyu.book.dao.pojo;

import java.io.Serializable;
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