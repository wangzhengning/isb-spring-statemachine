package com.statemachine.examples.tasks;

import com.statemachine.examples.tasks.enumpk.EnumEvents4Tasks;
import com.statemachine.examples.tasks.enumpk.EnumStates4Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * Created by zn.wang on 17/4/25.
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig4Tasks extends StateMachineConfigurerAdapter<EnumStates4Tasks, EnumEvents4Tasks> {

    private static final Logger logger = LoggerFactory.getLogger(StateMachineConfig4Tasks.class);

    @Override
    public void configure(StateMachineConfigurationConfigurer<EnumStates4Tasks, EnumEvents4Tasks> config) {
        try {
            //可以添加listener
//                config.withConfiguration()
//                        .listener(listener());
        } catch (Exception e) {
            buildException(config.getClass().getName(), e);
        }
    }

    @Override
    public void configure(StateMachineStateConfigurer<EnumStates4Tasks, EnumEvents4Tasks> states) {
        try {
            buildStateMachineStateConfigurer(states);
        } catch (Exception e) {
            buildException(states.getClass().getName(), e);
        }
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EnumStates4Tasks, EnumEvents4Tasks> transitions) {
        try {
            buildStateMachineTransitionConfigurer(transitions);
        } catch (Exception e) {
            buildException(transitions.getClass().getName(), e);
        }
    }


    private void buildStateMachineStateConfigurer(
            StateMachineStateConfigurer<EnumStates4Tasks, EnumEvents4Tasks> states) throws Exception {
        states
                .withStates()
                .initial(EnumStates4Tasks.READY)
                .fork(EnumStates4Tasks.FORK)
                .state(EnumStates4Tasks.TASKS)
                .join(EnumStates4Tasks.JOIN)
                .choice(EnumStates4Tasks.CHOICE)
                .state(EnumStates4Tasks.ERROR)
                .and()
                .withStates()
                .parent(EnumStates4Tasks.TASKS)
                .initial(EnumStates4Tasks.T1)
                .end(EnumStates4Tasks.T1E)
                .and()
                .withStates()
                .parent(EnumStates4Tasks.TASKS)
                .initial(EnumStates4Tasks.T2)
                .end(EnumStates4Tasks.T2E)
                .and()
                .withStates()
                .parent(EnumStates4Tasks.TASKS)
                .initial(EnumStates4Tasks.T3)
                .end(EnumStates4Tasks.T3E)
                .and()
                .withStates()
                .parent(EnumStates4Tasks.ERROR)
                .initial(EnumStates4Tasks.AUTOMATIC)
                .state(EnumStates4Tasks.AUTOMATIC, automaticAction(), null)
                .state(EnumStates4Tasks.MANUAL);
    }


    private void buildStateMachineTransitionConfigurer(
            StateMachineTransitionConfigurer<EnumStates4Tasks, EnumEvents4Tasks> transitions) throws Exception {
        transitions
                .withExternal()
                .source(EnumStates4Tasks.READY).target(EnumStates4Tasks.FORK)
                .event(EnumEvents4Tasks.RUN)
                .and()
                .withFork()
                .source(EnumStates4Tasks.FORK).target(EnumStates4Tasks.TASKS)
                .and()
                .withExternal()
                .source(EnumStates4Tasks.T1).target(EnumStates4Tasks.T1E)
                .and()
                .withExternal()
                .source(EnumStates4Tasks.T2).target(EnumStates4Tasks.T2E)
                .and()
                .withExternal()
                .source(EnumStates4Tasks.T3).target(EnumStates4Tasks.T3E)
                .and()
                .withJoin()
                .source(EnumStates4Tasks.TASKS).target(EnumStates4Tasks.JOIN)
                .and()
                .withExternal()
                .source(EnumStates4Tasks.JOIN).target(EnumStates4Tasks.CHOICE)
                .and()
                .withChoice()
                .source(EnumStates4Tasks.CHOICE)
                .first(EnumStates4Tasks.ERROR, tasksChoiceGuard())
                .last(EnumStates4Tasks.READY)
                .and()
                .withExternal()
                .source(EnumStates4Tasks.ERROR).target(EnumStates4Tasks.READY)
                .event(EnumEvents4Tasks.CONTINUE)
                .and()
                .withExternal()
                .source(EnumStates4Tasks.AUTOMATIC).target(EnumStates4Tasks.MANUAL)
                .event(EnumEvents4Tasks.FALLBACK)
                .and()
                .withInternal()
                .source(EnumStates4Tasks.MANUAL)
                .action(fixAction())
                .event(EnumEvents4Tasks.FIX);
    }

    @Bean
    public Action<EnumStates4Tasks, EnumEvents4Tasks> automaticAction() {
        return new Action<EnumStates4Tasks, EnumEvents4Tasks>() {

            @Override
            public void execute(StateContext<EnumStates4Tasks, EnumEvents4Tasks> context) {
                Map<Object, Object> variables = context.getExtendedState().getVariables();
                if (ObjectUtils.nullSafeEquals(variables.get("T1"), true)
                        && ObjectUtils.nullSafeEquals(variables.get("T2"), true)
                        && ObjectUtils.nullSafeEquals(variables.get("T3"), true)) {
                    context.getStateMachine().sendEvent(EnumEvents4Tasks.CONTINUE);
                } else {
                    context.getStateMachine().sendEvent(EnumEvents4Tasks.FALLBACK);
                }
            }
        };
    }

    @Bean
    public Guard<EnumStates4Tasks, EnumEvents4Tasks> tasksChoiceGuard() {
        return new Guard<EnumStates4Tasks, EnumEvents4Tasks>() {

            @Override
            public boolean evaluate(StateContext<EnumStates4Tasks, EnumEvents4Tasks> context) {
                Map<Object, Object> variables = context.getExtendedState().getVariables();
                return !(ObjectUtils.nullSafeEquals(variables.get("T1"), true)
                        && ObjectUtils.nullSafeEquals(variables.get("T2"), true)
                        && ObjectUtils.nullSafeEquals(variables.get("T3"), true));
            }
        };
    }

    @Bean
    public Action<EnumStates4Tasks, EnumEvents4Tasks> fixAction() {
        return new Action<EnumStates4Tasks, EnumEvents4Tasks>() {

            @Override
            public void execute(StateContext<EnumStates4Tasks, EnumEvents4Tasks> context) {
                Map<Object, Object> variables = context.getExtendedState().getVariables();
                variables.put("T1", true);
                variables.put("T2", true);
                variables.put("T3", true);
                context.getStateMachine().sendEvent(EnumEvents4Tasks.CONTINUE);
            }
        };
    }

    private static void buildException(String msg, Exception e) {
        logger.error("[ERRORS-IN-FUNTION] , CLASS-NAME = " + msg, e);
    }
}










