package com.statemachine.examples.shopping;

import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineFunction;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zn.wang on 17/5/11.
 */
@Service("iOrdersStateHandler")
public class IOrdersStateHandler extends AbstractOrdersStateHandler{

    @Resource(name = "iOrderStateMachine")
    private StateMachine<States, Events> stateMachine;

    @Resource(name = "iConsumerListener")
    private IOrderStateChangeListener listener;

    @Override
    protected void onInit() throws Exception {
        System.out.println ("onInit先执行");
        stateMachine.stop();
        stateMachine
                .getStateMachineAccessor()
                .doWithAllRegions(new StateMachineFunction<StateMachineAccess<States, Events>> () {
                    @Override
                    public void apply(StateMachineAccess<States, Events> function) {

                        function.setInitialEnabled ( false );
                        function.addStateMachineInterceptor ( new StateMachineInterceptorAdapter<States , Events> (){

                            @Override
                            public Message<Events> preEvent(Message<Events> message, StateMachine<States, Events> stateMachine) {
                                listener.preEvent ( message ,stateMachine);
                                return message;
                            }

                            @Override
                            public void preStateChange( State<States, Events> state, Message<Events> message, Transition<States, Events> transition,
                                                        StateMachine<States, Events> stateMachine) {
                                listener.preStateChange ( state , message , transition , stateMachine );
                            }

                            @Override
                            public void postStateChange(State<States, Events> state, Message<Events> message, Transition<States, Events> transition,
                                                        StateMachine<States, Events> stateMachine) {
                                listener.postStateChange ( state , message ,transition , stateMachine );
                            }

                            @Override
                            public StateContext<States, Events> preTransition( StateContext<States, Events> stateContext) {
                                listener.preTransition ( stateContext );
                                return stateContext;
                            }

                            @Override
                            public StateContext<States, Events> postTransition(StateContext<States, Events> stateContext) {
                                listener.postTransition(stateContext);
                                return stateContext;
                            }

                            @Override
                            public Exception stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
                                return exception;
                            }
                        });
                    }
                });

        stateMachine.start();
    }

    @Override
    protected void doDestroy() {
        System.out.println ("doDestroy再执行");
        stateMachine.stop ();
    };

    public void handleEvent(States sourceState , Message<Events> event)
    {
        handleEvent(sourceState , event, null );
    }

    public void handleEvent(States sourceState , Message<Events> event, ExtendedState extendedState)
    {
        stateMachine
                .getStateMachineAccessor()
                .doWithAllRegions(access -> access.resetStateMachine(
                        new DefaultStateMachineContext<States, Events> (sourceState, null, null, extendedState)));
        stateMachine.sendEvent(event);
    }


}
