package com.aspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RomeoHotelPapaQuebecApplication {

	public static void main(String[] args) {
		SpringApplication.run(RomeoHotelPapaQuebecApplication.class, args);
	}

	@RequestMapping("/getList")
	public String getList() {
		return "Here is the list";
	}
}
