package com.statemachine.examples.shopping;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

/**
 * Created by zn.wang on 17/5/11.
 */
public abstract class IOrderStateChangeListener<S , E> extends StateMachineListenerAdapter<S , E> {

    public abstract void preEvent( Message<E> message, StateMachine<S, E> stateMachine);

    public abstract void preStateChange( State<S, E> state, Message<E> message, Transition<S, E> transition,
                                StateMachine<S, E> stateMachine);

    public abstract void postStateChange(State<S, E> state, Message<E> message, Transition<S, E> transition,
                                StateMachine<S, E> stateMachine);

    public abstract void preTransition( StateContext<S, E> stateContext);

    public abstract void postTransition(StateContext<S, E> stateContext);

    public abstract void stateMachineError(StateMachine<S, E> stateMachine, Exception exception);

}
