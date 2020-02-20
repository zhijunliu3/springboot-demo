package com.pactera.demo.user.mapper;

import com.pactera.demo.common.mapper.BaseMapper;
import com.pactera.demo.user.entity.UserDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 */

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserDO> {
    /**
     * 通过用户ID获取用户信息
     * @param id
     * @return
     */
    @Select("select id,name,sex from user where id = #{id}")
    UserDO getUserById(Integer id);

    @Select("select id,name,sex from user")
    List<UserDO> getUsers();

    @SelectProvider(type = UserSql.class,method = "getUsersByCondition")
    List<UserDO> getUsersByCondition(UserDO user);
}
