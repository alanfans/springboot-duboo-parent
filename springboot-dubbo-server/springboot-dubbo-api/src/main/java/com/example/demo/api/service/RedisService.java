package com.example.demo.api.service;

import com.example.demo.api.model.Categories;
import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Path("redisService") // #1
//@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
public interface RedisService {

    void TestRedisLock(String lockString,String unlockString,Integer c);

    Long RedisMQpublish(String topic, Integer userId, String name);

    void submitJob(Map jobParams,String type, Long delay, TimeUnit timeUnit);

    @POST
    @Path("addUrl2Redis")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    Boolean addUrl2Redis(@QueryParam("type") String type, @QueryParam("url") String url);

    String addUrl2Redis(Categories categories);
}
