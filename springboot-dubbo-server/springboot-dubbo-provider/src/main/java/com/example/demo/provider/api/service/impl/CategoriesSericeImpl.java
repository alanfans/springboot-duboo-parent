package com.example.demo.provider.api.service.impl;

import com.example.demo.api.model.Categories;
import com.example.demo.api.service.CategoriesService;
import com.example.demo.provider.dao.CategoriesMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CategoriesSericeImpl implements CategoriesService {

    @Autowired
    CategoriesMapper categoriesMapper;

    @Override
    public int insert(Categories categories) {
        return categoriesMapper.insert(categories);
    }

    @Override
    public Categories select(Categories categories) {
        return categoriesMapper.select(categories);
    }

    @Override
    public int update(Categories categories) {
        return categoriesMapper.updateByPrimaryKey(categories);
    }
}
