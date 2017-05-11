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
public class IConsumerListener<States, Events> implements IOrderStateChangeListener<States,Events> {
    @Autowired
    private OrderService repository;

    private final static Log log = LogFactory.getLog(IConsumerListener.class);

    @Override
    public void stateChanged ( State <States, Events> from, State <States, Events> to ) {
        System.out.println ("stateChanged:{} , from:" +  from
                + " , to:" +  to );
    }

    @Override
    public void stateEntered ( State <States, Events> state ) {
        System.out.println ("stateEntered:{} , state:" + state );
    }

    @Override
    public void stateExited ( State <States, Events> state ) {
        System.out.println ("stateExited:{} , state:" +  state );
    }

    @Override
    public void eventNotAccepted ( Message <Events> event ) {
        System.out.println ("eventNotAccepted:{} , event:" + event );
    }

    @Override
    public void transition ( Transition <States, Events> transition ) {
        System.out.println ("transition:{} , transition:" +  transition );
    }

    @Override
    public void transitionStarted ( Transition <States, Events> transition ) {
        System.out.println ("transitionStarted:{} , transition:" +  transition);
    }

    @Override
    public void transitionEnded ( Transition <States, Events> transition ) {
        System.out.println ("transitionEnded:{} , stateMachine:" +  transition );
    }

    @Override
    public void stateMachineStarted ( StateMachine <States, Events> stateMachine ) {
        System.out.println ("stateMachineStarted:{} , stateMachine:" + stateMachine );
    }

    @Override
    public void stateMachineStopped ( StateMachine <States, Events> stateMachine ) {
        System.out.println ("stateMachineStopped:{} , stateMachine:" + stateMachine );
    }

    @Override
    public void stateMachineError ( StateMachine <States, Events> stateMachine, Exception exception ) {
        System.out.println ("stateMachineError:{} , stateMachine:" +  stateMachine
                +"exception:" +   exception);
    }

    @Override
    public void extendedStateChanged ( Object key, Object value ) {
        System.out.println ("extendedStateChanged:{} , key:" + key
                +"value:" + value);
    }

    @Override
    public void stateContext ( StateContext <States, Events> stateContext ) {
        System.out.println ("stateContext:{} , stateContext:" + stateContext);
    }

}
