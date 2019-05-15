package com.example.demo.api.service;

import org.apache.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 */

//@Path("accountService") // #1
//@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
//@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
public interface AccountService {
    void addAmount(Long userId, Integer amount);

    void removeAmount(Long userId, Integer amount);
}
