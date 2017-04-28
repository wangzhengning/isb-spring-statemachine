package com.statemachine.application;

import com.statemachine.constant.EnumPaymentEvent;
import com.statemachine.constant.EnumPaymentOrderStatus;
import com.statemachine.service.StateMachineConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineSystemConstants;

/**
 * Created by zn.wang on 17/4/21.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StateMachineApplication implements CommandLineRunner{

    protected Logger logger = LoggerFactory.getLogger(StateMachineApplication.class);

    protected AnnotationConfigApplicationContext context;

    protected StateMachine<EnumPaymentOrderStatus ,EnumPaymentEvent> stateMachine;

    protected void buildContext() {
        context = new AnnotationConfigApplicationContext();
        if(null == context){
            logger.info("[ERRORS] [AnnotationConfigApplicationContext is null]");
        }

        context.register(StateMachineConfig.class);
        context.refresh();

        stateMachine = (StateMachine<EnumPaymentOrderStatus , EnumPaymentEvent>)
                context.getBean(StateMachineSystemConstants.DEFAULT_ID_STATEMACHINE);
        if(null == stateMachine){
            logger.info("[ERRORS] [StateMachine is null]");
        }
    }

    @Override
    public void run(String... strings) throws Exception {
        buildContext();
        stateMachine.start();
        stateMachine.sendEvent(EnumPaymentEvent.PAY);
        stateMachine.sendEvent(EnumPaymentEvent.RECEIVE);
    }

    public static void main(String[] args) {
        SpringApplication.run(StateMachineApplication.class , args);
    }

}
