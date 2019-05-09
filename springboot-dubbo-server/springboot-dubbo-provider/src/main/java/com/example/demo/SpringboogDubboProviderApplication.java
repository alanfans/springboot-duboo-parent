package com.example.demo;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@EnableDubbo
@SpringBootApplication
@MapperScan("com.example.demo.provider.dao")
@DubboComponentScan(basePackages = "com.example")
public class SpringboogDubboProviderApplication {

	public static void main(String[] args){
		SpringApplication.run(SpringboogDubboProviderApplication.class, args);

	}


}
