package com.statemachine.examples.turnstile;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * Created by zn.wang on 17/4/25.
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig4TurnStile extends
        StateMachineConfigurerAdapter<EnumStates4TurnStile , EnumEvents4TurnStile>{

    @Override
    public void configure(StateMachineStateConfigurer<EnumStates4TurnStile, EnumEvents4TurnStile> states) throws Exception {
        states.withStates()
                .initial(EnumStates4TurnStile.LOCKED)
                .states(EnumSet.allOf(EnumStates4TurnStile.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EnumStates4TurnStile, EnumEvents4TurnStile> transitions) throws Exception {
        transitions.withExternal()
                .source(EnumStates4TurnStile.LOCKED)
                .target(EnumStates4TurnStile.UNLOCKED)
                .event(EnumEvents4TurnStile.COIN)
                .and()
                .withExternal()
                .source(EnumStates4TurnStile.UNLOCKED)
                .target(EnumStates4TurnStile.LOCKED)
                .event(EnumEvents4TurnStile.PUSH);
    }
}
