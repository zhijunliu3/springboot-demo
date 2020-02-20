package com.pactera.demo.user.entity;

import com.pactera.demo.annotation.TableInfo;
import lombok.Data;

@Data
@TableInfo(tableName = "token",
        primaryKey = "id")
public class TokenDO {
    private String token;
    private Integer userId;
}
