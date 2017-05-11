package com.statemachine.examples.shopping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

/**
 * Created by zn.wang on 17/5/11.
 */
@Service("iConsumerListener")
public class IConsumerListener<States, Events> extends IOrderStateChangeListener<States,Events> {
    @Autowired
    private OrderService repository;

    private final static Log log = LogFactory.getLog(IConsumerListener.class);

    @Override
    public void preEvent ( Message <Events> message, StateMachine <States, Events> stateMachine ) {
        System.out.println ("preEvent:{} , message:" + message +" , stateMachine:" + stateMachine);
    }

    @Override
    public void preStateChange ( State <States, Events> state, Message <Events> message, Transition <States, Events> transition, StateMachine <States, Events> stateMachine ) {
        System.out.println ("preStateChange:{} , state:" + state +" , message:" + message + " , transition:"+ transition +" , stateMachine:" + stateMachine);
    }

    @Override
    public void postStateChange ( State <States, Events> state, Message <Events> message, Transition <States, Events> transition, StateMachine <States, Events> stateMachine ) {
        System.out.println ("postStateChange:{} , state:" + state +" , message:" + message + " , transition:"+ transition +" , stateMachine:" + stateMachine);
    }

    @Override
    public void preTransition ( StateContext <States, Events> stateContext ) {
        System.out.println ("preTransition:{} , stateContext:" + stateContext );
    }

    @Override
    public void postTransition ( StateContext <States, Events> stateContext ) {
        System.out.println ("postTransition:{} , stateContext:" + stateContext );
    }

    @Override
    public void stateMachineError ( StateMachine <States, Events> stateMachine, Exception exception ) {
        System.out.println ("状态机发生异常啦.....");
    }
}
