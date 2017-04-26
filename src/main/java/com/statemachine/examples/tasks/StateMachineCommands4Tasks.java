package com.statemachine.examples.tasks;

import com.statemachine.examples.common.AbstractStateMachineCommands;
import com.statemachine.examples.tasks.enumpk.EnumEvents4Tasks;
import com.statemachine.examples.tasks.enumpk.EnumStates4Tasks;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Created by zn.wang on 17/4/23.
 */
@Component
public class StateMachineCommands4Tasks extends AbstractStateMachineCommands<EnumStates4Tasks, EnumEvents4Tasks> {

    @CliCommand(value = "sm event" , help = "Sends an event to a state machine")
    public String event(@CliOption(key = {"" , "event"} , mandatory =true , help = "The event")
                            final EnumEvents4Tasks event){
        getStateMachine().sendEvent(event);
        return "Event " + event + " send";
    }
}


















