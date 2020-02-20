package com.pactera.demo.user.entity;

import com.pactera.demo.annotation.TableInfo;
import lombok.Data;

@Data
@TableInfo(tableName = "user_role",
        primaryKey = "id")
public class UserRoleDO {
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
