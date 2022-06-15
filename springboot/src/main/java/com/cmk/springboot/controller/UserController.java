package com.cmk.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmk.springboot.common.Result;
import com.cmk.springboot.entity.User;
import com.cmk.springboot.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping
    public Result<User> save(@RequestBody User user) {
        if (StringUtils.isBlank(user.getPassword()))
            user.setPassword("000000");
        userMapper.insert(user);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPag(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getNickName, search);
        }
        Page<User> page = userMapper.selectPage(new Page<>(pageIndex, pageSize), wrapper);
        return Result.success(page);
    }

    @PutMapping
    public Result<User> update(@RequestBody User user) {
        if (StringUtils.isBlank(user.getPassword()))
            user.setPassword("000000");
        userMapper.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        int i = userMapper.deleteById(id);
        return Result.success();
    }
}
