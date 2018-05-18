package com.shiro.mapper;


import com.shiro.bean.User;

public interface UserMapper {
    /**
     * 根据Id查询用户
     * @return
     */
    User getUserById(Integer id);
}
