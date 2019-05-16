package com.example.demo.api.service;

import com.example.demo.api.model.Categories;
import io.swagger.annotations.Api;
import org.apache.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("categoriesService") // #1
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.APPLICATION_JSON_UTF_8})
@Api(value = "categoriesService", description = "User REST for Integration Testing")
public interface CategoriesService {


    public static String ALLITEBOOKS_URL = "http://www.allitebooks.org/";

    public static String ONEBOOK_URL = "http://www.allitebooks.org/google-app-inventor/";

    int insert(Categories categories);

    Categories select(Categories categories);

    int update(Categories categories1);

    List<Categories> selectByType(Categories categories);
}
