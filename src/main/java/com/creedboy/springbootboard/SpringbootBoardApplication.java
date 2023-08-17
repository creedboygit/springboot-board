package com.creedboy.springbootboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
//@EnableJpaRepositories
//@EnableJpaAuditing
public class SpringbootBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBoardApplication.class, args);
    }

}
