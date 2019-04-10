package com.example.demo.provider.api.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.demo.api.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hylc on 11/4/18.
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleMail(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("admin@alimama.tw");
        simpleMailMessage.setTo("617907659@qq.com");
        simpleMailMessage.setSubject("test");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        simpleMailMessage.setText("测试工aasdfsadfsf"+ sdf.format(date));

        try{
            mailSender.send(simpleMailMessage);
            System.out.println("send success!");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("send faild");
        }
    }
}
