package com.example.demo.api.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.*;

/**
 * Created by hylc on 11/4/18.
 */
//@Path("mailService") // #1
//@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
//@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
//@Api(value = "MailService", description = "User REST for Integration Testing")
public interface MailService {

    @POST
    @Path("sendSimpleMail")
    @ApiOperation(value = "sendSimpleMail")
    void sendSimpleMail();
}
