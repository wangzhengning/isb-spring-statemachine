package com.statemachine.examples.persist.handle;

import com.statemachine.examples.persist.Service.PersistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;

/**
 * Created by zn.wang on 17/4/26.
 */
@Configuration
public class PersistHandleConfig {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @Bean
    public PersistService persist() {
        return new PersistService(persistStateMachineHandler());
    }

    @Bean
    public PersistStateMachineHandler persistStateMachineHandler() {
        return new PersistStateMachineHandler(stateMachine);
    }
}
