package com.pactera.demo.user.service;

import com.github.pagehelper.PageInfo;
import com.pactera.demo.user.entity.UserDO;

import java.util.List;

/**
 * 用户服务类
 */
public interface UserService {
    /**
     * 通过用户Id获取用户信息
     * @param id
     * @return
     */
    UserDO getUserById(Integer id) throws Exception;

    /**
     * 获取用户信息列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UserDO> getUsers(int pageNum, int pageSize);

    /**
     * 通过条件查询用户列表
     * @param user
     * @return
     */
    List<UserDO> getUsersByCondition(UserDO user);

    List<UserDO> getAllUser();

    void addUser(UserDO user);
}
