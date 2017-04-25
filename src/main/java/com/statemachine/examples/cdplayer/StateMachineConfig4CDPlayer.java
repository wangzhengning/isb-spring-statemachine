package com.statemachine.examples.cdplayer;

import com.statemachine.examples.cdplayer.Action.*;
import com.statemachine.examples.cdplayer.entity.Library;
import com.statemachine.examples.cdplayer.enumpk.EnumEvents4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumStates4CDPlayer;
import com.statemachine.examples.cdplayer.guard.PlayGuard;
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
public class StateMachineConfig4CDPlayer extends StateMachineConfigurerAdapter<EnumStates4CDPlayer, EnumEvents4CDPlayer>{
    private static final Logger logger = LoggerFactory.getLogger(StateMachineConfig4CDPlayer.class);

    @Override
    public void configure(StateMachineConfigurationConfigurer<EnumStates4CDPlayer, EnumEvents4CDPlayer> config) {
        try {
            //可以添加listener
//                config.withConfiguration()
//                        .listener(listener());
        } catch (Exception e) {
            buildException(config.getClass().getName(), e);
        }
    }

    @Override
    public void configure(StateMachineStateConfigurer<EnumStates4CDPlayer, EnumEvents4CDPlayer> states) {
        try {
            buildStateMachineStateConfigurer(states);
        } catch (Exception e) {
            buildException(states.getClass().getName(), e);
        }
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EnumStates4CDPlayer, EnumEvents4CDPlayer> transitions) {
        try {
            buildStateMachineTransitionConfigurer(transitions);
        } catch (Exception e) {
            buildException(transitions.getClass().getName(), e);
        }
    }

    @Bean
    public ClosedEntryAction closedEntryAction(){
        return new ClosedEntryAction();
    }

    @Bean
    public LoadAction loadAction(){
        return new LoadAction();
    }

    @Bean
    public TrackAction trackAction(){
        return new TrackAction();
    }

    @Bean
    public PlayAction playAction(){
        return new PlayAction();
    }

    @Bean
    public PlayingAction playingAction(){
        return new PlayingAction();
    }

    @Bean
    public PlayGuard playGuard(){
        return new PlayGuard();
    }

    @Bean
    public CDPlayer cdPlayer() {
        return new CDPlayer();
    }

    @Bean
    public Library library() {
        return Library.buildSampleLibrary();
    }

    private void buildStateMachineStateConfigurer(
            StateMachineStateConfigurer<EnumStates4CDPlayer, EnumEvents4CDPlayer> states) throws Exception {
        states
                .withStates()
                .initial(EnumStates4CDPlayer.IDLE)
                .state(EnumStates4CDPlayer.IDLE)
                .and()
                .withStates()
                .parent(EnumStates4CDPlayer.IDLE)
                .initial(EnumStates4CDPlayer.CLOSED)
                .state(EnumStates4CDPlayer.CLOSED, closedEntryAction(), null)
                .state(EnumStates4CDPlayer.OPEN)
                .and()
                .withStates()
                .state(EnumStates4CDPlayer.BUSY)
                .and()
                .withStates()
                .parent(EnumStates4CDPlayer.BUSY)
                .initial(EnumStates4CDPlayer.PLAYING)
                .state(EnumStates4CDPlayer.PLAYING)
                .state(EnumStates4CDPlayer.PAUSED);

    }

    private void buildStateMachineTransitionConfigurer(
            StateMachineTransitionConfigurer<EnumStates4CDPlayer, EnumEvents4CDPlayer> transitions) throws Exception {
        transitions
                .withExternal()
                .source(EnumStates4CDPlayer.CLOSED).target(EnumStates4CDPlayer.OPEN).event(EnumEvents4CDPlayer.EJECT)
                .and()
                .withExternal()
                .source(EnumStates4CDPlayer.OPEN).target(EnumStates4CDPlayer.CLOSED).event(EnumEvents4CDPlayer.EJECT)
                .and()
                .withExternal()
                .source(EnumStates4CDPlayer.OPEN).target(EnumStates4CDPlayer.CLOSED).event(EnumEvents4CDPlayer.PLAY)
                .and()
                .withExternal()
                .source(EnumStates4CDPlayer.PLAYING).target(EnumStates4CDPlayer.PAUSED).event(EnumEvents4CDPlayer.PAUSE)
                .and()
                .withInternal()
                .source(EnumStates4CDPlayer.PLAYING)
                .action(playingAction())
                .timer(1000)
                .and()
                .withInternal()
                .source(EnumStates4CDPlayer.PLAYING).event(EnumEvents4CDPlayer.BACK)
                .action(trackAction())
                .and()
                .withInternal()
                .source(EnumStates4CDPlayer.PLAYING).event(EnumEvents4CDPlayer.FORWARD)
                .action(trackAction())
                .and()
                .withExternal()
                .source(EnumStates4CDPlayer.PAUSED).target(EnumStates4CDPlayer.PLAYING).event(EnumEvents4CDPlayer.PAUSE)
                .and()
                .withExternal()
                .source(EnumStates4CDPlayer.BUSY).target(EnumStates4CDPlayer.IDLE).event(EnumEvents4CDPlayer.STOP)
                .and()
                .withExternal()
                .source(EnumStates4CDPlayer.IDLE).target(EnumStates4CDPlayer.BUSY).event(EnumEvents4CDPlayer.PLAY)
                .action(playAction())
                .guard(playGuard())
                .and()
                .withInternal()
                .source(EnumStates4CDPlayer.OPEN).event(EnumEvents4CDPlayer.LOAD).action(loadAction());

    }

    private static void buildException(String msg, Exception e) {
        logger.error("[ERRORS-IN-FUNTION] , CLASS-NAME = " + msg, e);
    }

}
