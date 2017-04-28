package com.statemachine.examples.washer;

import com.statemachine.util.IsbBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created by zn.wang on 17/4/26.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application4Washer implements CommandLineRunner{
    private static final Logger logger = LoggerFactory.getLogger(Application4Washer.class);

    private static final String []contextPath = {
            "classpath*:/META-INF/spring/spring-shell-plugin.xml" ,
            "classpath*:/META-INF/spring/spring-shell-plugin-washer.xml"
    };

    @Override
    public void run(String... args) throws Exception {
        try{
            IsbBootstrap.main(args , contextPath);
        }catch (Exception e){
            System.out.println("[ERRORS-START-TASKS]");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application4Washer.class , args);
    }

}
