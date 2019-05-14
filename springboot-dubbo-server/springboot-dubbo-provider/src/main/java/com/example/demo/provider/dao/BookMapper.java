package com.example.demo.provider.dao;

import com.example.demo.api.model.Book;
import org.apache.ibatis.annotations.Param;

public interface BookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKeyWithBLOBs(Book record);

    int updateByPrimaryKey(Book record);

    Book selectbyName(@Param("bookTitie") String bookTitie);
}