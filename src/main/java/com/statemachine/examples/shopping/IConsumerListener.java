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

import java.util.Map;

/**
 * Created by zn.wang on 17/5/11.
 */
@Service("iConsumerListener")
public class IConsumerListener extends IOrderStateChangeListener<States , Events> {
    @Autowired
    private OrderService repository;

    private final static Log logger = LogFactory.getLog(IConsumerListener.class);

    @Override
    public void preEvent ( Message <Events> message, StateMachine <States, Events> stateMachine ) {
        //logger.info ("preEvent:{} , message:" + message +" , stateMachine:" + stateMachine);
    }

    @Override
    public void preStateChange ( State <States, Events> state, Message <Events> message, Transition <States, Events> transition, StateMachine <States, Events> stateMachine ) {
        logger.info ("preStateChange:{} , state:" + state +" , message:" + message + " , transition:"+ transition +" , stateMachine:" + stateMachine);

        try{
            String orderId = String.valueOf ( message.getHeaders ().get ( "order-id" ));
            Map<Object, Object> objectMap = stateMachine.getExtendedState ().getVariables ();

            System.out.println ("orderId:" + orderId + " , " + objectMap);

            Order updateOrder = (Order) objectMap.get ( orderId );
            updateOrder.setState (state.getId ().name ());

            logger.info ("orderId:" + orderId +" , updateOrder:" + updateOrder);

            repository.save ( updateOrder );

        }catch (Exception e){
            e.printStackTrace ();
        }

    }

    @Override
    public void postStateChange ( State <States, Events> state, Message <Events> message, Transition <States, Events> transition, StateMachine <States, Events> stateMachine ) {
        //logger.info ("postStateChange:{} , state:" + state +" , message:" + message + " , transition:"+ transition +" , stateMachine:" + stateMachine);
    }

    @Override
    public void preTransition ( StateContext <States, Events> stateContext ) {
        //logger.info ("preTransition:{} , stateContext:" + stateContext );
    }

    @Override
    public void postTransition ( StateContext <States, Events> stateContext ) {
       //logger.info ("postTransition:{} , stateContext:" + stateContext );
    }

    @Override
    public void smError ( StateMachine <States, Events> stateMachine, Exception exception ) {
        //logger.info ("状态机发生异常啦.....");
    }
}
