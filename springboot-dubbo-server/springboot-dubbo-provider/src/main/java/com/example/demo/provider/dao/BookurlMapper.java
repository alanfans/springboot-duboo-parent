package com.example.demo.provider.dao;


import com.example.demo.api.model.Bookurl;

public interface BookurlMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bookurl record);

    int insertSelective(Bookurl record);

    Bookurl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bookurl record);

    int updateByPrimaryKey(Bookurl record);
}