package com.example.demo.api.service;

import com.example.demo.api.model.Downloadlink;

import javax.ws.rs.Path;

@Path("downloadlinkService")
public interface DownloadlinkService {

    int addDownloadlink(Downloadlink downloadlink);
}
