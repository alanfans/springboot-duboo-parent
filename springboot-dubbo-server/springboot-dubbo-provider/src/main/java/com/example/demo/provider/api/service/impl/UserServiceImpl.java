package com.example.demo.provider.api.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.demo.api.service.UserService;
import com.example.demo.provider.dao.UserMapper;
import com.example.demo.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hylc on 2018/8/19.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findOneById(Long id) {
        return userMapper.findOneById(id);
    }
}
