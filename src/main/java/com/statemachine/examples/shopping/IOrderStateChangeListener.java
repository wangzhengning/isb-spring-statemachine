package com.statemachine.examples.shopping;

import org.springframework.statemachine.listener.StateMachineListener;

/**
 * Created by zn.wang on 17/5/11.
 */
public interface IOrderStateChangeListener<S , E> extends StateMachineListener<S, E>{
}
