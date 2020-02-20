package com.pactera.demo.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pactera.demo.common.exception.FASErrorCodeEnum;
import com.pactera.demo.common.exception.FASException;
import com.pactera.demo.user.entity.RoleDO;
import com.pactera.demo.user.entity.UserDO;
import com.pactera.demo.user.mapper.RoleMapper;
import com.pactera.demo.user.mapper.UserMapper;
import com.pactera.demo.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDO getUserById(Integer id) throws Exception {
        logger.info(id+"");
        if(id == 2){
            throw new FASException(FASErrorCodeEnum.ERROR_90001);
        }
        System.out.println(id);
        return userMapper.getUserById(id);
    }

    @Override
    public PageInfo<UserDO> getUsers(int pageNum, int pageSize) {
        //改写语句实现分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<UserDO> all = userMapper.getUsers();
        PageInfo<UserDO> info = new PageInfo<>(all);
        return info;
    }

    @Override
    public List<UserDO> getUsersByCondition(UserDO user) {
        return userMapper.getUsersByCondition(user);
    }

    @Override
    public List<UserDO> getAllUser() {
        List<RoleDO> select = roleMapper.selectAll(null);
        select.forEach(item->{
            System.out.println(item.getRemake());
        });
        RoleDO roleDO = new RoleDO();
        roleDO.setId(2);
        Map<String, Object> ids = new HashMap<>();
        ids.put("id", 1);
        RoleDO roleDO1 = roleMapper.selectByPK(ids);
        System.out.println(roleDO1.getRemake());
        return userMapper.getUsers();
    }

    @Override
    public void addUser(UserDO user) {
        userMapper.insert(user);
    }


}
