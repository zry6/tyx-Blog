package com.zry.simpleBlog.comment.config;

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

/**
 * @author zry
 * @create 2022-04-21 17:50
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).
// 指定构建 api 文档的详细信息的方法：
        apiInfo(apiInfo()).apiInfo(apiInfo())
                .select()
// 指定要生成 api 接口的包路径，这里把 controller 作为包路径，生成 controller 中的所有接口
                .apis(RequestHandlerSelectors.basePackage("com.zry.simpleBlog.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 api 文档的详细信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
// 设置页面标题
                .title("retwis-->接口文档")
                .contact(new Contact("小而美个人博客项目开发", "", "1447051936@qq.com"))
                // 设置接口描述
                .description("retwis的Api")
// 设置版本
                .version("1.0")
// 构建
                .build();
    }
}