package com.example.demo.provider.api.service.impl;


import com.example.demo.api.model.Account;
import com.example.demo.api.service.AccountService;
import com.example.demo.provider.dao.AccountMapper;
import com.example.demo.provider.dao.UserMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AcountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void addAmount(Long userId, Integer amount) {
        Account account =  accountMapper.selectByPrimaryKey(userId);
        account.setAmount(account.getAmount()+amount);
        accountMapper.updateByPrimaryKey(account);
    }

    @Override
    public void removeAmount(Long userId, Integer amount) {
        Account account =  accountMapper.selectByPrimaryKey(userId);
        if(account.getAmount()-amount <0){
            userMapper.findOneById(account.getUserid());
            throw new RuntimeException("");
        }
        account.setAmount(account.getAmount()-amount);
        accountMapper.updateByPrimaryKey(account);
    }
}
