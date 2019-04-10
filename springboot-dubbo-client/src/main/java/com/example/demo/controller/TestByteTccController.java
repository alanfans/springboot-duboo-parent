package com.example.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.api.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tetByte")
public class TestByteTccController {
    private static Logger logger = LoggerFactory.getLogger(TestByteTccController.class);

    @Reference
    AccountService accountService;


    @PostMapping("testAddAcount")
    public ResponseEntity testAddAcount(Long userId, Integer amount) {
        accountService.addAmount(userId,amount);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("testRemoveAcount")
    public ResponseEntity testRemoveAcount(Long userId, Integer amount) {
        try{
            accountService.removeAmount(userId,amount);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
