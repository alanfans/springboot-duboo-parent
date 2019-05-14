package com.example.demo.provider.dao;


import com.example.demo.api.model.Categories;

import java.util.List;

public interface CategoriesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Categories record);

    int insertSelective(Categories record);

    Categories selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Categories record);

    int updateByPrimaryKey(Categories record);

    Categories select(Categories record);

    List<Categories> selectByType(Categories categories);
}