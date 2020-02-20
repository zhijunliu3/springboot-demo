package com.pactera.demo.user.entity;

import com.pactera.demo.annotation.TableInfo;
import lombok.Data;

@Data
@TableInfo(tableName = "role",
        primaryKey = "id")
public class RoleDO {
    private Integer id;
    private String remake;
}
