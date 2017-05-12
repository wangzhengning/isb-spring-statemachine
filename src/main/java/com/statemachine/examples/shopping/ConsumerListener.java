package com.statemachine.examples.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Service;

/**
 * Created by zn.wang on 17/5/10.
 */
@Service
public class ConsumerListener implements OrderStateChangeListener<States , Events>{

    @Autowired
    private OrderService repository;

    @Override
    public void onStateChange ( State<States, Events> state, Message<Events> message ){
        System.out.println ("Order state changed , update db , " + state.getId ());

        try{
            String orderId = message.getHeaders().get("order-id", String.class);
            Order order = repository.findOne(orderId);
            order.setState(state.getId().name ());
            repository.save(order);
            int b = 5/0;
        }
        catch (Exception e){

        }
        finally {

        }

    }

    @Override
    public void stateMachineError ( StateMachine<States, Events> stateMachine, Exception exception ) {
        try{
            System.out.println ("===状态机发生异常啦=====" + stateMachine + ";" + exception);
        }
        catch (Exception e){

        }
        finally {

        }
    }

}






