package com.example.demo.api.service;

import com.example.demo.api.model.User;
import org.apache.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by hylc on 2018/8/19.
 */
@Path("userService") // #1
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
public interface UserService {

    User findOneById(Long id);
}
