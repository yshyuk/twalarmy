package com.today.weather.alarmy;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@MapperScan("com.today.weather.alarmy.dao")
@Slf4j
public class AlarmyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlarmyApplication.class, args);
    }

}
