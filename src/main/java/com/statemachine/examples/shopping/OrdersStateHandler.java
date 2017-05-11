package com.statemachine.examples.shopping;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineFunction;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by zn.wang on 17/5/10.
 */
@Service
public class OrdersStateHandler extends AbstractOrdersStateHandler {

    @Resource(name = "orderStateMachine")
    private StateMachine<States, Events> stateMachine;

    private Set<OrderStateChangeListener> listeners = new HashSet<> ();

    protected void onInit() throws Exception {
        System.out.println ("onInit先执行");
        stateMachine.stop();
        stateMachine
                .getStateMachineAccessor()
                .doWithAllRegions(new StateMachineFunction<StateMachineAccess<States, Events>>() {
                    @Override
                    public void apply(StateMachineAccess<States, Events> function) {
                        function.addStateMachineInterceptor ( new StateMachineInterceptorAdapter<States , Events> (){
                            @Override
                            public void preStateChange( State<States , Events> state, Message<Events> message, Transition<States , Events> transition,
                                                        StateMachine<States , Events> stateMachine) {
                                listeners.forEach(listener -> listener.onStateChange(state, message) );
                            }

                            @Override
                            public Exception stateMachineError(StateMachine<States , Events> stateMachine, Exception exception) {
                                listeners.forEach(listener -> listener.stateMachineError(stateMachine, exception) );
                                return null;
                            }

                        } );
                    }
                });
        stateMachine.start();
    }

    @Override
    protected void doDestroy() {
        System.out.println ("doDestroy再执行");
        stateMachine.stop ();
    };


    public void registerListener(OrderStateChangeListener listener) {
        listeners.add(listener);
    }

    public void handleEvent(States sourceState , Message event)
    {
        handleEvent(sourceState , event, null );
    }

    public void handleEvent(States sourceState , Message event, Map<String, Object> eventHeaders)
    {
        stateMachine
                .getStateMachineAccessor()
                .doWithAllRegions(access -> access.resetStateMachine(
                        new DefaultStateMachineContext<States, Events> (sourceState, null, null, null)));
        stateMachine.sendEvent(event);
    }

}
