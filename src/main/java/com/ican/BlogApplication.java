package com.ican;

import com.ican.config.properties.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 博客启动类
 *
 * @author Dduo
 * @date 2024/11/28 18:54
 **/
@SpringBootApplication
@EnableConfigurationProperties(DatabaseProperties.class)
public class BlogApplication {

    // 项目启动入口
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}