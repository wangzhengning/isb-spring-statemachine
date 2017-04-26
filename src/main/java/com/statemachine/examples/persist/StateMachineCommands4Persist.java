package com.statemachine.examples.persist;

import com.statemachine.examples.common.AbstractStateMachineCommands;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Created by zn.wang on 17/4/23.
 */
@Component
public class StateMachineCommands4Persist extends AbstractStateMachineCommands<String, String> {

    @CliCommand(value = "sm event" , help = "Sends an event to a state machine")
    public String event(@CliOption(key = {"" , "event"} , mandatory =true , help = "The event")
                            final String event){
        getStateMachine().sendEvent(event);
        return "Event " + event + " send";
    }
}


















