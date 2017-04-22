package com.statemachine.service;

import com.statemachine.constant.EnumPaymentEvent;
import com.statemachine.constant.EnumPaymentOrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * Created by zn.wang on 17/4/21.
 * 创建状态机配置类
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<EnumPaymentOrderStatus, EnumPaymentEvent> {
    private final Logger logger = LoggerFactory.getLogger(StateMachineConfig.class);

    @Override
    public void configure(StateMachineStateConfigurer<EnumPaymentOrderStatus, EnumPaymentEvent> states)
            throws Exception {
        states.withStates()
                .initial(EnumPaymentOrderStatus.UNPAID)
                .states(EnumSet.allOf(EnumPaymentOrderStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EnumPaymentOrderStatus, EnumPaymentEvent> transitions)
            throws Exception {
        transitions.withExternal()
                .source(EnumPaymentOrderStatus.UNPAID).target(EnumPaymentOrderStatus.WAITING_FOR_RECEIVE)
                .event(EnumPaymentEvent.PAY)
                .and()
                .withExternal()
                .source(EnumPaymentOrderStatus.WAITING_FOR_RECEIVE).target(EnumPaymentOrderStatus.DONE)
                .event(EnumPaymentEvent.RECEIVE);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<EnumPaymentOrderStatus, EnumPaymentEvent> config)
            throws Exception {
        config.withConfiguration()
                .listener(listener());
    }

    @Bean
    public StateMachineListener<EnumPaymentOrderStatus, EnumPaymentEvent> listener() {
        return new StateMachineListenerAdapter<EnumPaymentOrderStatus, EnumPaymentEvent>() {

            @Override
            public void transition(Transition<EnumPaymentOrderStatus, EnumPaymentEvent> transition) {
                if(transition.getTarget().getId() == EnumPaymentOrderStatus.UNPAID){
                    logger.info("订单创建,待支付");
                    return;
                }
                if(transition.getSource().getId() == EnumPaymentOrderStatus.UNPAID
                        && transition.getTarget().getId() == EnumPaymentOrderStatus.WAITING_FOR_RECEIVE){
                    logger.info("用户完成支付,待收货");
                }
                if(transition.getSource().getId() == EnumPaymentOrderStatus.WAITING_FOR_RECEIVE
                        && transition.getTarget().getId() == EnumPaymentOrderStatus.DONE){
                    logger.info("用户已收货,订单完成");
                }

            }

        };
    }


}



















