package com.statemachine.examples.zookeeper;

import com.statemachine.util.IsbBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zn.wang on 17/4/26.
 */
@Configuration
public class Application4Zookeeper implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(Application4Zookeeper.class);

    private static final String []contextPath = {
            "classpath*:/META-INF/spring/spring-shell-plugin.xml" ,
            "classpath*:/META-INF/spring/spring-shell-plugin-zookeeper.xml"
    };

    @Override
    public void run(String... args) throws Exception {
        try{
            IsbBootstrap.main(args , contextPath);
        }catch (Exception e){
            System.out.println("[ERRORS-START-TURNSTILE]");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application4Zookeeper.class , args);
    }


}
