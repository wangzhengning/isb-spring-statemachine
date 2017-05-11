package com.statemachine.examples.shopping;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.ExtendedState;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zn.wang on 17/5/11.
 */
@Service("iOrderApplication")
public class IOrderApplication {

    @Resource(name = "iOrdersStateHandler")
    private IOrdersStateHandler handler;

    public void execute( String orderId ,  Events preSendEvent , States  currentState){
        execute ( orderId , preSendEvent , currentState ,null );
    }

    /**
     * 向状态机发送事件
     * @param orderId 当前订单
     * @param preSendEvent 发送的事件
     * @param currentState 当前订单状态
     * @param extendedState 当前订单的附属信息
     */
    public void execute( String orderId , Events preSendEvent, States currentState, ExtendedState extendedState){

        handler.handleEvent(currentState,
                MessageBuilder
                        .withPayload(preSendEvent)
                        .setHeader("order-id", orderId)
                        .build() , extendedState);
    }


}
