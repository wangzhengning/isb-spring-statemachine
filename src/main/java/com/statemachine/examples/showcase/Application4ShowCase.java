package com.statemachine.examples.showcase;

import com.statemachine.util.IsbBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by zn.wang on 17/4/24.
 */
@SpringBootApplication
public class Application4ShowCase implements CommandLineRunner{


    private static final Logger logger = LoggerFactory.getLogger(Application4ShowCase.class);

    private static final String []contextPath = {
            "classpath*:/META-INF/spring/spring-shell-plugin.xml" ,
            "classpath*:/META-INF/spring/spring-shell-plugin-showcase.xml"
    };

    @Override
    public void run(String... args) throws Exception {
        try{
            IsbBootstrap.main(args , contextPath);
        }catch (Exception e){
            System.out.println("[ERRORS-START-SHOWCASE]");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application4ShowCase.class , args);
    }

}
















