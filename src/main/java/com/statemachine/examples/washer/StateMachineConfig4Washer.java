package com.statemachine.examples.washer;

import com.statemachine.examples.washer.enumpk.EnumEvents4Washer;
import com.statemachine.examples.washer.enumpk.EnumStates4Washer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;

/**
 * Created by zn.wang on 17/4/25.
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig4Washer extends StateMachineConfigurerAdapter<EnumStates4Washer, EnumEvents4Washer> {

    private static final Logger logger = LoggerFactory.getLogger(StateMachineConfig4Washer.class);

    @Override
    public void configure(StateMachineConfigurationConfigurer<EnumStates4Washer, EnumEvents4Washer> config) {
        try {
            //可以添加listener
//                config.withConfiguration()
//                        .listener(listener());
        } catch (Exception e) {
            buildException(config.getClass().getName(), e);
        }
    }

    @Override
    public void configure(StateMachineStateConfigurer<EnumStates4Washer, EnumEvents4Washer> states) {
        try {
            buildStateMachineStateConfigurer(states);
        } catch (Exception e) {
            buildException(states.getClass().getName(), e);
        }
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EnumStates4Washer, EnumEvents4Washer> transitions) {
        try {
            buildStateMachineTransitionConfigurer(transitions);
        } catch (Exception e) {
            buildException(transitions.getClass().getName(), e);
        }
    }


    private void buildStateMachineStateConfigurer(
            StateMachineStateConfigurer<EnumStates4Washer, EnumEvents4Washer> states) throws Exception {
        states
                .withStates()
                .initial(EnumStates4Washer.RUNNING)
                .state(EnumStates4Washer.POWEROFF)
                .end(EnumStates4Washer.END)
                .and()
                .withStates()
                .parent(EnumStates4Washer.RUNNING)
                .initial(EnumStates4Washer.WASHING)
                .state(EnumStates4Washer.RINSING)
                .state(EnumStates4Washer.DRYING)
                .history(EnumStates4Washer.HISTORY, StateConfigurer.History.SHALLOW);
    }


    private void buildStateMachineTransitionConfigurer(
            StateMachineTransitionConfigurer<EnumStates4Washer, EnumEvents4Washer> transitions) throws Exception {
        transitions
                .withExternal()
                .source(EnumStates4Washer.WASHING).target(EnumStates4Washer.RINSING)
                .event(EnumEvents4Washer.RINSE)
                .and()
                .withExternal()
                .source(EnumStates4Washer.RINSING).target(EnumStates4Washer.DRYING)
                .event(EnumEvents4Washer.DRY)
                .and()
                .withExternal()
                .source(EnumStates4Washer.RUNNING).target(EnumStates4Washer.POWEROFF)
                .event(EnumEvents4Washer.CUTPOWER)
                .and()
                .withExternal()
                .source(EnumStates4Washer.POWEROFF).target(EnumStates4Washer.HISTORY)
                .event(EnumEvents4Washer.RESTOREPOWER)
                .and()
                .withExternal()
                .source(EnumStates4Washer.RUNNING).target(EnumStates4Washer.END)
                .event(EnumEvents4Washer.STOP);
    }

    private static void buildException(String msg, Exception e) {
        logger.error("[ERRORS-IN-FUNTION] , CLASS-NAME = " + msg, e);
    }
}










