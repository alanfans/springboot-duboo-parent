package com.example.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.api.model.User;
import com.example.demo.api.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hylc on 2018/8/19.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 注入服务提供方暴露的接口，通过@Reference注解，dubbo会在扫描的时候自动代理接口，然后通过rpc调用远程服务。
     * 如果用xml配置方式，需要将@Reference换成@Autowired。
     */
    @Reference
    UserService userService;

    @RequestMapping("/find")
    @ResponseBody
    public User findOneById(Integer id){
        System.out.println("------Controller--------id------"+id);
        User user = userService.findOneById(id);
        if(user == null){
            user = new User();
        }
        return user;
    }
}
