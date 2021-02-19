package com.rzk;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableApolloConfig //开启swagger,logger需要开启这个注解
@MapperScan("com.rzk.mapper")
@SpringBootApplication
public class ImRambleSysApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ImRambleSysApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ImRambleSysApplication.class, args);
    }

}
