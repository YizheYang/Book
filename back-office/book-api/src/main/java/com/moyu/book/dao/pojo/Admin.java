package com.moyu.book.dao.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName admin
 */
@Data
public class Admin implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    private static final long serialVersionUID = 1L;
}