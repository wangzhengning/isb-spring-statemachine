package com.statemachine.service;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.statemachine.constant.EnumPaymentEvent;
import com.statemachine.constant.EnumPaymentOrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zn.wang on 17/4/28.
 */
public class StateMachineConfigBuilder {

    private static final Logger logger = LoggerFactory.getLogger(StateMachineConfigBuilder.class);

    public static StateMachine<String, String> buildMachine() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        builder.configureStates()
                .withStates()
                .initial(EnumPaymentOrderStatus.UNPAID.getCode())
                .states(new Function<EnumSet<EnumPaymentOrderStatus>, Set<String>>() {
                    @Override
                    public Set<String> apply(EnumSet<EnumPaymentOrderStatus> input) {
                        Set<String> stringSet = Sets.newHashSet();
                        Iterator<EnumPaymentOrderStatus> iterator = input.iterator();
                        while (iterator.hasNext()) {
                            EnumPaymentOrderStatus element = iterator.next();
                            stringSet.add(element.getCode());
                        }
                        return stringSet;
                    }
                }.apply(EnumSet.allOf(EnumPaymentOrderStatus.class)));

        builder.configureTransitions()
                .withExternal()
                .source(EnumPaymentOrderStatus.UNPAID.getCode()).target(EnumPaymentOrderStatus.WAITING_FOR_RECEIVE.getCode())
                .event(EnumPaymentEvent.PAY.getCode())
                .and()
                .withExternal()
                .source(EnumPaymentOrderStatus.WAITING_FOR_RECEIVE.getCode()).target(EnumPaymentOrderStatus.DONE.getCode())
                .event(EnumPaymentEvent.RECEIVE.getCode());


        builder.configureConfiguration()
                .withConfiguration()
                .beanFactory(new StaticListableBeanFactory())
                .listener(listener());

        return builder.build();
    }


    public static StateMachineListener<String, String> listener() {
        return new StateMachineListenerAdapter<String, String>() {
            @Override
            public void transition(Transition<String, String> transition) {
                if (transition.getTarget().getId().equals(EnumPaymentOrderStatus.UNPAID.getCode())) {
                    logger.info("订单创建,待支付");
                    return;
                }
                if (transition.getSource().getId().equals(EnumPaymentOrderStatus.UNPAID.getCode())
                        && transition.getTarget().getId().equals(EnumPaymentOrderStatus.WAITING_FOR_RECEIVE.getCode())) {
                    logger.info("用户完成支付,待收货");
                }
                if (transition.getSource().getId().equals(EnumPaymentOrderStatus.WAITING_FOR_RECEIVE.getCode())
                        && transition.getTarget().getId().equals(EnumPaymentOrderStatus.DONE.getCode())) {
                    logger.info("用户已收货,订单完成");
                }

            }

        };
    }


    public static void main(String[] args) {
        try {
            StateMachine<String, String> stateMachine = buildMachine();
            stateMachine.start();
            logger.info("[INFOS] before sent event:{} , machine current state:{} ", EnumPaymentEvent.PAY.getCode(), stateMachine.getState().getId());
            stateMachine.sendEvent(EnumPaymentEvent.PAY.getCode());
            logger.info("[INFOS] after sent event:{} , machine current state:{} ", EnumPaymentEvent.PAY.getCode(), stateMachine.getState().getId());
            System.out.println("===============================");
            logger.info("[INFOS] before sent event:{} , machine current state:{} ", EnumPaymentEvent.RECEIVE.getCode(), stateMachine.getState().getId());
            stateMachine.sendEvent(EnumPaymentEvent.RECEIVE.getCode());
            logger.info("[INFOS] after sent event:{} , machine current state:{} ", EnumPaymentEvent.RECEIVE.getCode(), stateMachine.getState().getId());
        }
        catch (Exception e) {
            logger.debug("[ERRORS-EXECUTE-BUILD-MACHINE-STATEMACHINE]" , e);
            e.printStackTrace();
        }
    }


}
