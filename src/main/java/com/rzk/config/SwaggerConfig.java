package com.rzk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static org.reflections.util.ConfigurationBuilder.build;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rzk.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        Contact contact = new Contact("楷哥", "http://ruizhukai.com", "1769073060@qq.com");
        return new ApiInfo(
                "API接口",
                "API接口文档",
                "1.0",
                "2.9.2",
        contact,
                "##",
                "##",
                new ArrayList());
    };

    /*
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API接口")
                .description("API接口文档")
                .version("2.9.2")
                .termsOfServiceUrl("https://www.jiyeyihe.com")
                .build();
    }*/
}
