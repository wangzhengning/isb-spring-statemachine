package com.statemachine.examples.shopping;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;

/**
 * Created by zn.wang on 17/5/10.
 */
public interface OrderStateChangeListener<States , Events>{
    public void onStateChange ( State<States, Events> state, Message<Events> message );
    public void stateMachineError( StateMachine<States, Events> stateMachine, Exception exception);
}
