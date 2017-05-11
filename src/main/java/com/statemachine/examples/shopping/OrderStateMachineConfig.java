package com.statemachine.examples.shopping;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * Created by zn.wang on 17/5/10.
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
//                .listener ( new StateMachineListener () );
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.ORDERED)
                .end(States.PAYED)
                .states( EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.ORDERED)
                .target(States.ASSEMBLED)
                .event(Events.assemble)
                .and()
                .withExternal()
                .source(States.ASSEMBLED)
                .target(States.DELIVERED)
                .event(Events.deliver)
                .and()
                .withExternal()
                .source(States.DELIVERED)
                .target(States.INVOICED)
                .event(Events.release_invoice)
                .and()
                .withExternal()
                .source(States.INVOICED)
                .target(States.PAYED)
                .event(Events.payment_received)
                .and()
                .withExternal()
                .source(States.ORDERED)
                .target(States.CANCELLED)
                .event(Events.cancel)
                .and()
                .withExternal()
                .source(States.ASSEMBLED)
                .target(States.CANCELLED)
                .event(Events.cancel)
                .and()
                .withExternal()
                .source(States.DELIVERED)
                .target(States.RETURNED)
                .event(Events.claim)
                .and()
                .withExternal()
                .source(States.INVOICED)
                .target(States.RETURNED)
                .event(Events.claim)
                .and()
                .withExternal()
                .source(States.RETURNED)
                .target(States.CANCELLED)
                .event(Events.cancel)
                .and()
                .withExternal()
                .source(States.RETURNED)
                .target(States.ASSEMBLED)
                .event(Events.reassemble);
    }

//    private static final class StateMachineListener extends StateMachineListenerAdapter<States, Events> {
//        private static Logger logger = LoggerFactory.getLogger ( StateMachineListener.class );
//        @Override
//        public void stateChanged( State<States, Events> from, State<States, Events> to) {
//            System.out.println ( "[StateMachineListener Monitor] Order state changed to " + to.getId ());
//        }
//    }

}
