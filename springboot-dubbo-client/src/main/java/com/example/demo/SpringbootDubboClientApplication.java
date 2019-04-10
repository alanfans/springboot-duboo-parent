package com.example.demo;

import org.bytesoft.bytetcc.supports.dubbo.config.DubboSecondaryConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Import(DubboSecondaryConfiguration.class)
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ImportResource("classpath:consumer.xml")
public class SpringbootDubboClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDubboClientApplication.class, args);
	}
}
