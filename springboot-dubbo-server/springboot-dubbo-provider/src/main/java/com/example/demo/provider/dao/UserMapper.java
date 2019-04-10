package com.example.demo.provider.dao;

import com.example.demo.api.model.User;

/**
 * Created by hylc on 2018/8/19.
 */
public interface UserMapper {

    User findOneById(Long id);
}
