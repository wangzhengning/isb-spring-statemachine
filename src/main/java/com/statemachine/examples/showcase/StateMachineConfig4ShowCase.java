package com.statemachine.examples.showcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * Created by zn.wang on 17/4/25.
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig4ShowCase extends StateMachineConfigurerAdapter<EnumStates4ShowCase, EnumEvents4ShowCase> {

    private static final Logger logger = LoggerFactory.getLogger(StateMachineConfig4ShowCase.class);

    @Override
    public void configure(StateMachineConfigurationConfigurer<EnumStates4ShowCase, EnumEvents4ShowCase> config) {
        try {
            //可以添加listener
//                config.withConfiguration()
//                        .listener(listener());
        } catch (Exception e) {
            buildException(config.getClass().getName(), e);
        }
    }

    @Override
    public void configure(StateMachineStateConfigurer<EnumStates4ShowCase, EnumEvents4ShowCase> states) {
        try {
            buildStateMachineStateConfigurer(states);
        } catch (Exception e) {
            buildException(states.getClass().getName(), e);
        }
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EnumStates4ShowCase, EnumEvents4ShowCase> transitions) {
        try {
            buildStateMachineTransitionConfigurer(transitions);
        } catch (Exception e) {
            buildException(transitions.getClass().getName(), e);
        }
    }


    @Bean
    public FooGuard foo0Guard() {
        return new FooGuard(0);
    }

    @Bean
    public FooGuard foo1Guard() {
        return new FooGuard(1);
    }

    @Bean
    public FooAction fooAction() {
        return new FooAction();
    }

    private void buildStateMachineStateConfigurer(
            StateMachineStateConfigurer<EnumStates4ShowCase, EnumEvents4ShowCase> states) throws Exception {
        states.withStates()
                .initial(EnumStates4ShowCase.S0, fooAction())
                .state(EnumStates4ShowCase.S0)
                .and()
                .withStates()
                .parent(EnumStates4ShowCase.S0)
                .initial(EnumStates4ShowCase.S1)
                .state(EnumStates4ShowCase.S1)
                .and()
                .withStates()
                .parent(EnumStates4ShowCase.S1)
                .initial(EnumStates4ShowCase.S11)
                .state(EnumStates4ShowCase.S11)
                .state(EnumStates4ShowCase.S12)
                .and()
                .withStates()
                .parent(EnumStates4ShowCase.S0)
                .state(EnumStates4ShowCase.S2)
                .and()
                .withStates()
                .parent(EnumStates4ShowCase.S2)
                .initial(EnumStates4ShowCase.S21)
                .state(EnumStates4ShowCase.S21)
                .and()
                .withStates()
                .parent(EnumStates4ShowCase.S21)
                .initial(EnumStates4ShowCase.S211)
                .state(EnumStates4ShowCase.S211)
                .state(EnumStates4ShowCase.S212);

    }

    private void buildStateMachineTransitionConfigurer(
            StateMachineTransitionConfigurer<EnumStates4ShowCase, EnumEvents4ShowCase> transitions) throws Exception {

        transitions
                .withExternal()
                .source(EnumStates4ShowCase.S1).target(EnumStates4ShowCase.S1).event(EnumEvents4ShowCase.A)
                .guard(foo1Guard())
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S1).target(EnumStates4ShowCase.S11).event(EnumEvents4ShowCase.B)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S21).target(EnumStates4ShowCase.S211).event(EnumEvents4ShowCase.B)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S1).target(EnumStates4ShowCase.S2).event(EnumEvents4ShowCase.C)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S2).target(EnumStates4ShowCase.S1).event(EnumEvents4ShowCase.C)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S1).target(EnumStates4ShowCase.S0).event(EnumEvents4ShowCase.D)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S211).target(EnumStates4ShowCase.S21).event(EnumEvents4ShowCase.D)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S0).target(EnumStates4ShowCase.S211).event(EnumEvents4ShowCase.E)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S1).target(EnumStates4ShowCase.S211).event(EnumEvents4ShowCase.F)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S2).target(EnumStates4ShowCase.S11).event(EnumEvents4ShowCase.F)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S11).target(EnumStates4ShowCase.S211).event(EnumEvents4ShowCase.G)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S211).target(EnumStates4ShowCase.S0).event(EnumEvents4ShowCase.G)
                .and()
                .withInternal()
                .source(EnumStates4ShowCase.S0).event(EnumEvents4ShowCase.H)
                .guard(foo0Guard())
                .action(fooAction())
                .and()
                .withInternal()
                .source(EnumStates4ShowCase.S2).event(EnumEvents4ShowCase.H)
                .guard(foo1Guard())
                .action(fooAction())
                .and()
                .withInternal()
                .source(EnumStates4ShowCase.S1).event(EnumEvents4ShowCase.H)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S11).target(EnumStates4ShowCase.S12).event(EnumEvents4ShowCase.I)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S211).target(EnumStates4ShowCase.S212).event(EnumEvents4ShowCase.I)
                .and()
                .withExternal()
                .source(EnumStates4ShowCase.S12).target(EnumStates4ShowCase.S212).event(EnumEvents4ShowCase.I);

    }

    private static void buildException(String msg, Exception e) {
        logger.error("[ERRORS-IN-FUNTION] , CLASS-NAME = " + msg, e);
    }
}










