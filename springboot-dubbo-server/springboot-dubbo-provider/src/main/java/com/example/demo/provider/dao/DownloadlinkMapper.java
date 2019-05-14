package com.example.demo.provider.dao;


import com.example.demo.api.model.Downloadlink;

public interface DownloadlinkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Downloadlink record);

    int insertSelective(Downloadlink record);

    Downloadlink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Downloadlink record);

    int updateByPrimaryKey(Downloadlink record);
}