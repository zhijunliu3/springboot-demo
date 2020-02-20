package com.pactera.demo.user.entity;

import com.pactera.demo.annotation.TableInfo;
import lombok.Data;

@Data
@TableInfo(tableName = "role_auth",
        primaryKey = "id")
public class RoleAuthDO {
    private Integer id;
    private Integer roleId;
    private Integer authId;
}
