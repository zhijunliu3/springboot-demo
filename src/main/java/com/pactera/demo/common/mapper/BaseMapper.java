package com.pactera.demo.common.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

    @InsertProvider(type = BaseSql.class, method = "getCommonSql")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insert(T t);

    @UpdateProvider(type = BaseSql.class, method = "getCommonSql")
    void update(T t);

    @DeleteProvider(type = BaseSql.class, method = "getCommonSql")
    void delete(T t);

    @SelectProvider(type = BaseSql.class,method = "getCommonSql")
    List<T> selectAll(T t);

    @SelectProvider(type = BaseSql.class,method = "getCommonSql")
    T selectByPK(Map<String, Object> ids);
}
