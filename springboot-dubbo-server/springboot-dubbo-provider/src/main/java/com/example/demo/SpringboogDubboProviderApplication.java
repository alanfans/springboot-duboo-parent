package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@MapperScan("com.example.demo.provider.dao")
@ImportResource("classpath:provider.xml")
public class SpringboogDubboProviderApplication {

	public static void main(String[] args) throws InterruptedException{
		SpringApplication.run(SpringboogDubboProviderApplication.class, args);
		//pom中没有加spring-boot-starter-web依赖，启动时没有tomcat容器，会自动退出，所以加了一个sleep防止自动退出
		//Thread.sleep(Long.MAX_VALUE);
	}
}
