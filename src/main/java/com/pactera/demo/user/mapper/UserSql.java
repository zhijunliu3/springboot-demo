package com.pactera.demo.user.mapper;

import com.pactera.demo.user.entity.UserDO;
import org.springframework.util.StringUtils;

/**
 * 拼接用户sql
 */
public class UserSql {
    public String getUsersByCondition(UserDO user){
        StringBuilder sb = new StringBuilder();
        sb.append("select id,name,sex from user where 1=1");
        if(!StringUtils.isEmpty(user.getName())){
            sb.append(" and name like '%").append(user.getName()).append("%'");
        }
        if(user.getSex() != null){
            sb.append(" and sex = ").append(user.getSex());
        }
        return sb.toString();
    }

    public String getSql(){
        return "";
    }
}
