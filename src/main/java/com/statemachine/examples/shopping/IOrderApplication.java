package com.statemachine.examples.shopping;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zn.wang on 17/5/11.
 */
@Service("iOrderApplication")
public class IOrderApplication {

    @Resource(name = "iOrdersStateHandler")
    private IOrdersStateHandler handler;

    public void execute( String orderId , Events preSendEvent , States  currentState){

        handler.handleEvent(currentState,
                MessageBuilder
                        .withPayload(preSendEvent)
                        .setHeader("order-id", orderId)
                        .build());
    }
}
