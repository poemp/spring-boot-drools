package org.poem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 曹莉
 */
@EnableScheduling
@SpringBootApplication
@MapperScan("org.poem")
@EnableConfigurationProperties
public class SpringBootDroolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDroolsApplication.class, args);
    }

}
