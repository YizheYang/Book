package com.moyu.book.dao.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@Data
public class User implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 时长id
     */
    private Long timeId;

    private static final long serialVersionUID = 1L;
}