package org.poem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("org")
public class SpringBootDroolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDroolsApplication.class, args);
    }

}
