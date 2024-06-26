package com.avengers.fury;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableCaching
@SpringBootApplication
public class FuryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuryApplication.class, args);
	}

}
