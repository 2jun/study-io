package com.aj.study.iostudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class IoStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoStudyApplication.class, args);
	}

	@RequestMapping("/test")
	public Object test() throws InterruptedException {
		while (true){
			TimeUnit.SECONDS.sleep(10);
		}
	}
}
