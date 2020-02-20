package com.pactera.demo.user.mapper;

import com.pactera.demo.common.mapper.BaseMapper;
import com.pactera.demo.user.entity.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<RoleDO> {
    @Select("select t4.auth_code from token t left join user_role t2 on t.user_id = t2.user_id" +
            " left join role_auth t3 on t2.role_id = t3.role_id left join auth t4 on t3.auth_id = t4.id" +
            " where t.token = #{token}")
    List<String> getAuthCodesByToken(String token);
}
