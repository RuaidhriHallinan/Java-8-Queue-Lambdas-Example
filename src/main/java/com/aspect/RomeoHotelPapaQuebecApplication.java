package com.aspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.aspect"})
public class RomeoHotelPapaQuebecApplication {

    public static void main(String[] args) {
        SpringApplication.run(RomeoHotelPapaQuebecApplication.class, args);


    }

}
