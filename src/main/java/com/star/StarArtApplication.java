package com.star;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.star.mapper")
@EnableCaching
public class StarArtApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarArtApplication.class, args);
    }

}
