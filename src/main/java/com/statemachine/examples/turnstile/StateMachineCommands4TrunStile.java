package com.statemachine.examples.turnstile;

import com.statemachine.examples.common.AbstractStateMachineCommands;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Created by zn.wang on 17/4/23.
 */
@Component
public class StateMachineCommands4TrunStile extends AbstractStateMachineCommands<EnumStates4TurnStile, EnumEvents4TurnStile> {

    @CliCommand(value = "sm event" , help = "Sends an event to a state machine")
    public String event(@CliOption(key = {"" , "event"} , mandatory =true , help = "The event")
                            final EnumEvents4TurnStile event){
        getStateMachine().sendEvent(event);
        return "Event " + event + " send";
    }
}


















