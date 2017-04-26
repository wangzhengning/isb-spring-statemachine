package com.statemachine.examples.washer;

import com.statemachine.examples.common.AbstractStateMachineCommands;
import com.statemachine.examples.washer.enumpk.EnumEvents4Washer;
import com.statemachine.examples.washer.enumpk.EnumStates4Washer;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Created by zn.wang on 17/4/23.
 */
@Component
public class StateMachineCommands4Washer extends AbstractStateMachineCommands<EnumStates4Washer, EnumEvents4Washer> {

    @CliCommand(value = "sm event" , help = "Sends an event to a state machine")
    public String event(@CliOption(key = {"" , "event"} , mandatory =true , help = "The event")
                            final EnumEvents4Washer event){
        getStateMachine().sendEvent(event);
        return "Event " + event + " send";
    }
}


















