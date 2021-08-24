package com.pactera.demo.context;

import com.pactera.demo.user.entity.UserDO;

/**
 * @author liuzj
 * @create 2021/8/24 11:16
 **/
public class UserContextHolder {
    public static ThreadLocal<UserDO> holder = new ThreadLocal<>();
}
