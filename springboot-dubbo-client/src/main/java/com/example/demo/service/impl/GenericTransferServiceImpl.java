package com.example.demo.service.impl;

import com.example.demo.service.TransferService;
//import org.apache.dubbo.config.annotation.Service;

//@Service(interfaceClass = GenericTransferServiceImpl.class, group = "x-bytetcc", filter = "bytetcc", loadbalance = "bytetcc", cluster = "failfast", retries = -1)
public class GenericTransferServiceImpl implements TransferService {
    @Override
    public void transfer(String sourceAcctId, String targetAcctId, double amount) {

    }
}
