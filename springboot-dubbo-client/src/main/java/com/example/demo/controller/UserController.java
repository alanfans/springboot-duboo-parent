package com.example.demo.controller;

import com.example.demo.api.model.User;
import com.example.demo.api.service.MailService;
import com.example.demo.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hylc on 2018/8/19.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 注入服务提供方暴露的接口，通过@Reference注解，dubbo会在扫描的时候自动代理接口，然后通过rpc调用远程服务。
     * 如果用xml配置方式，需要将@Reference换成@Autowired。
     */
    @Reference
    UserService userService;

    @Reference
    MailService mailService;

    @RequestMapping("/find")
    @ResponseBody
    public User findOneById(Long id){
        logger.info("------UserController--------id------" + id);
        logger.debug("This is a debug message-----UserController--------id------" + id);
        logger.info("This is an info message-----UserController--------id------" + id);
        logger.warn("This is a warn message-----UserController--------id------" + id);
        logger.error("This is an error message-----UserController--------id------" + id);

        User user = userService.findOneById(id);
        if(user == null){
            user = new User();
        }
        return user;
    }

    @RequestMapping("/springboot")
    public String Hello() {
        logger.debug("T念佛爱的解放军殴打司机开发ge");
        logger.info("This is 的解放军殴打司机开发 info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        return "Hello SpringBoot!";
    }

    @RequestMapping("/sendMail")
    public void sendMail(){
        mailService.sendSimpleMail();
    }
}
