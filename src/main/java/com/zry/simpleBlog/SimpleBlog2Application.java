package com.zry.simpleBlog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author 14470
 */
@SpringBootApplication
@MapperScan("com.zry.simpleBlog.mapper")
@PropertySource("classpath:/globalVariable.properties")
@EnableCaching
public class SimpleBlog2Application {

    public static void main(String[] args) {
        SpringApplication.run(SimpleBlog2Application.class, args);
    }
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
