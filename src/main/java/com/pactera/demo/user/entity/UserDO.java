package com.pactera.demo.user.entity;

import com.pactera.demo.annotation.TableInfo;
import lombok.Data;

/**
 * 用户实体类
 */
@Data
@TableInfo(tableName = "user",
        primaryKey = "id")
public class UserDO {

    /**
     * 用户Id
     */
    private Integer id;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户性别
     */
    private Integer sex;
}
