package com.example.demo.configuration;

//import com.deepoove.swagger.dubbo.annotations.EnableDubboSwagger;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by hylc on 2018/8/26.
 */
//@EnableDubboSwagger
@EnableSwagger2
@EnableDubboConfig
@Configuration
public class SwaggerDubboConfig {

    @Bean
    public Docket adminApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Admin API")
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(paths())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private Predicate<String> paths(){
        return Predicates.and(PathSelectors.regex("/.*"), Predicates.not(PathSelectors.regex("/error")));
    }
    private ApiInfo apiInfo(){
        Contact contact = new Contact("lance", "https://github.com/leelance", "81222045@qq.com");
        return new ApiInfoBuilder()
                .title("Document Api")
                .description("Spring-boot-Springfox Example")
                .license("Apache License Version 2.0")
                .contact(contact)
                .version("2.0")
                .build();
    }
}
