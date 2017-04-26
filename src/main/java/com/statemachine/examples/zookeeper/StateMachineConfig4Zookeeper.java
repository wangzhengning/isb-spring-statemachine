package com.statemachine.examples.zookeeper;

import com.google.common.collect.Sets;
import com.statemachine.examples.turnstile.EnumEvents4TurnStile;
import com.statemachine.examples.turnstile.EnumStates4TurnStile;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.ensemble.StateMachineEnsemble;
import org.springframework.statemachine.zookeeper.ZookeeperStateMachineEnsemble;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by zn.wang on 17/4/25.
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig4Zookeeper extends
        StateMachineConfigurerAdapter<String , String>{


    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config
                .withDistributed()
                .ensemble(stateMachineEnsemble());
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states.withStates()
                .initial(EnumStates4TurnStile.LOCKED.name())
                .states(buildEnumSet());
    }

    private Set<String> buildEnumSet(){
        Set<EnumStates4TurnStile> set = EnumSet.allOf(EnumStates4TurnStile.class);
        Set<String> buildSet = Sets.newHashSet();
        for(EnumStates4TurnStile enumSet : set){
            buildSet.add(enumSet.name());
        }
        return buildSet;
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions.withExternal()
                .source(EnumStates4TurnStile.LOCKED.name())
                .target(EnumStates4TurnStile.UNLOCKED.name())
                .event(EnumEvents4TurnStile.COIN.name())
                .and()
                .withExternal()
                .source(EnumStates4TurnStile.UNLOCKED.name())
                .target(EnumStates4TurnStile.LOCKED.name())
                .event(EnumEvents4TurnStile.PUSH.name());
    }


    @Bean
    public StateMachineEnsemble<String, String> stateMachineEnsemble() throws Exception {
        return new ZookeeperStateMachineEnsemble<String, String>(curatorClient(), "/foo");
    }

    @Bean
    public CuratorFramework curatorClient() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().defaultData(new byte[0])
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectString("localhost:2181").build();
        client.start();
        return client;
    }

}
