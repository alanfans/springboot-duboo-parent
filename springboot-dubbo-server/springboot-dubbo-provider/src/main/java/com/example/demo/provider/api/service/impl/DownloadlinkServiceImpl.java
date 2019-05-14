package com.example.demo.provider.api.service.impl;

import com.example.demo.api.model.Downloadlink;
import com.example.demo.api.service.DownloadlinkService;
import com.example.demo.provider.dao.DownloadlinkMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DownloadlinkServiceImpl implements DownloadlinkService {

    @Autowired
    DownloadlinkMapper downloadlinkMapper;

    @Override
    public int addDownloadlink(Downloadlink downloadlink) {
        downloadlinkMapper.insert(downloadlink);
        return downloadlink.getId();
    }
}
