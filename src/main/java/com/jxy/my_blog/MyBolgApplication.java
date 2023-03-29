package com.jxy.my_blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.jxy.my_blog.mapper"})
public class MyBolgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBolgApplication.class, args);
    }

}
