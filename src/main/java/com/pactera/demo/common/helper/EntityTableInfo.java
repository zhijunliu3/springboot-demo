package com.pactera.demo.common.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description: 实体表信息类
 * @author: LiuZhiJun
 * @create: 2019-08-08 17:41
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityTableInfo {
    private String tableName;
    private String primaryKey;
}
