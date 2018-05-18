package com.shiro.service.impl;

import com.shiro.bean.User;
import com.shiro.mapper.UserMapper;
import com.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public User queryUserById(Integer id) {
        User user = userMapper.getUserById(id);
        return user;
    }

}
