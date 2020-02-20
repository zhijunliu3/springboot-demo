package com.pactera.demo.user.mapper;

import com.pactera.demo.common.mapper.BaseMapper;
import com.pactera.demo.user.entity.TokenDO;
import com.pactera.demo.user.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TokenMapper extends BaseMapper<TokenDO> {
    @Select("select t2.id,t2.name,t2.sex from token t left join user t2 on t.user_id = t2.id where t.token = #{token}")
    UserDO getUserByToken(String token);
}
