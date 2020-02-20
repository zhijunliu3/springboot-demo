package com.pactera.demo.user.entity;

import com.pactera.demo.annotation.TableInfo;
import lombok.Data;

@Data
@TableInfo(tableName = "auth",
        primaryKey = "id")
public class AuthDO {
    private Integer id;
    private String authCode;
    private String remake;
}
