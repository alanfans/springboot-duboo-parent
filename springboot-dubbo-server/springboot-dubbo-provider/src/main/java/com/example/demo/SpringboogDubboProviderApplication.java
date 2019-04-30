package com.example.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.example.demo.provider.dao")
@EnableDubbo(scanBasePackages = "com.example")
public class SpringboogDubboProviderApplication {

	public static void main(String[] args) throws InterruptedException{
		SpringApplication.run(SpringboogDubboProviderApplication.class, args);
		//pom中没有加spring-boot-starter-web依赖，启动时没有tomcat容器，会自动退出，所以加了一个sleep防止自动退出
		//Thread.sleep(Long.MAX_VALUE);
	}
}
