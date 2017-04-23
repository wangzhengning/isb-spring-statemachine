package com.statemachine.examples.turnstile;

import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Bootstrap;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by zn.wang on 17/4/23.
 */
@Configuration
public class Application4TurnStile {


    @Configuration
    @EnableStateMachine
    static class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States , Events>{

        @Override
        public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
            states.withStates()
                    .initial(States.LOCKED)
                    .states(EnumSet.allOf(States.class));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
            transitions.withExternal()
                    .source(States.LOCKED)
                    .target(States.UNLOCKED)
                    .event(Events.COIN)
                    .and()
                    .withExternal()
                    .source(States.UNLOCKED)
                    .target(States.LOCKED)
                    .event(Events.PUSH);
        }
    }

    public enum States {
        LOCKED, UNLOCKED;
    }

    public enum Events {
        COIN, PUSH;
    }

    public static void main(String[] args) {
        try {
            Bootstrap.main(args);
        } catch (IOException e) {
            System.out.println("[ERRORS-START-TURNSTILE]");
            e.printStackTrace();
        }
    }
}














