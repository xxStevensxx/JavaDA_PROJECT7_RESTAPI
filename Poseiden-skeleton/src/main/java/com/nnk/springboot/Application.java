package com.nnk.springboot;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan(basePackages = {"com.nnk.springboot.repositories"})
@SpringBootApplication
public class Application {
	
	public final static Logger LOG =  LogManager.getLogger(Application.class.getName());
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		
		LOG.info("This is Info Log");


	}
}
