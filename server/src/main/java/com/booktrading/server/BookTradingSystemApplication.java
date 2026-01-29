package com.booktrading.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.booktrading.server.mapper")
public class BookTradingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookTradingSystemApplication.class, args);
    }
}
