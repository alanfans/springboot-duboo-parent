package com.example.demo.api.service;

import com.example.demo.api.model.User;

/**
 * Created by hylc on 2018/8/19.
 */
public interface UserService {

    User findOneById(Integer id);
}
