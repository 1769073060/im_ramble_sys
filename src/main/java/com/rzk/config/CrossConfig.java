package com.rzk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @PackageName : com.rzk.config
 * @FileName : CorsConfig
 * @Description : 跨域配置
 * @Author : rzk
 * @CreateTime : 1/2/2021 上午2:56
 * @Version : 1.0.0
 */
@Configuration
public class CrossConfig implements WebMvcConfigurer {

    /**
     * 配置跨域请求
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//指定路径
                .allowCredentials(true)//带上cookie信息
                .allowedHeaders("*")//允许任何请求头
                /**
                 * 默认情况下，所有的域名都是允许的
                 * "http://127.0.0.1:8080",
                 */
                .allowedOrigins("http://192.168.0.113")
                /**
                 * *设置HTTP方法为允许，例如{"GET"}， {"POST"}等。
                 * 默认“简单”方法{@code GET}， {@code HEAD}，和{@code POST}
                 * *是允许的。
                 */
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                /**
                 * 配置飞行前请求的响应时间(秒)
                 * 可以被客户端缓存。
                 * 默认为1800秒(30分钟)。
                 */
                .maxAge(3600);
    }
}
