package com.statemachine.examples.zookeeper;

import com.statemachine.examples.common.AbstractStateMachineCommands;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Created by zn.wang on 17/4/26.
 */
@Component
public class StateMachineCommands4Zookeeper extends AbstractStateMachineCommands{
    @CliCommand(value = "sm event", help = "Sends an event to a state machine")
    public String event(@CliOption(key = { "", "event" }, mandatory = true, help = "The event") final String event) {
        getStateMachine().sendEvent(event);
        return "Event " + event + " send";
    }
}










