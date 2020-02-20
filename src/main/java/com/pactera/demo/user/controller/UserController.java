package com.pactera.demo.user.controller;

import com.github.pagehelper.PageInfo;
import com.pactera.demo.annotation.AuthVerification;
import com.pactera.demo.annotation.LogInfo;
import com.pactera.demo.user.entity.UserDO;
import com.pactera.demo.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制类
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 通过用户ID获取用户信息
     * @param id
     * @return
     */
    @GetMapping(value="/getUserById")
    @ApiOperation(value = "通过ID获取用户信息", notes = "通过ID获取用户信息")
    @AuthVerification("user:view")
    @LogInfo("getUserId")
    public UserDO getUserById(@RequestParam Integer id) throws Exception {
        return userService.getUserById(id);
    }

    @GetMapping("/getUsers")
    @ApiOperation(value = "获取用户列表", notes = "获取所有的用户信息")
    public PageInfo<UserDO> getUsers(@RequestParam int pageNum, @RequestParam int pageSize) {
        return userService.getUsers(pageNum, pageSize);
    }

    @PostMapping("/getUsersByCondition")
    @ApiOperation(value = "通过条件查询用户列表", notes = "通过条件查询用户列表信息")
    public List<UserDO> getUsersByCondition(@RequestBody @ApiParam(value = "查询用户条件实体",required = true)UserDO user){
        return userService.getUsersByCondition(user);
    }

    @PostMapping("/addUser")
    @ApiOperation(value="新增用户", notes = "新增用户")
    public void addUser(@RequestBody @ApiParam(value = "用户实体",required = true) UserDO user){
        userService.addUser(user);
    }
}
