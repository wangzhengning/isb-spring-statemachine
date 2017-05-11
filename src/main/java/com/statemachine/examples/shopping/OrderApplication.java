package com.statemachine.examples.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by zn.wang on 17/5/10.
 */
@Service
public class OrderApplication {

    @Autowired
    private OrdersStateHandler ordersStateHandler;

    @Autowired
    private ConsumerListener consumerListener;

    public void execute( String orderId , Events preSendEvent , States  currentState){

        ordersStateHandler.registerListener ( consumerListener );

        ordersStateHandler.handleEvent(currentState,
                MessageBuilder
                        .withPayload(preSendEvent)
                        .setHeader("order-id", orderId)
                        .build());
    }

}
