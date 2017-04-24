package com.statemachine.examples.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by zn.wang on 17/4/23.
 */
@Component
public class AbstractStateMachineCommands<S ,E> implements CommandMarker {

    @Autowired
    private StateMachine<S, E> stateMachine;

    protected StateMachine<S, E> getStateMachine(){
        return stateMachine;
    }

    @CliCommand(value = "sm state" , help = "Prints current state")
    public String state(){
        State<S, E> state = stateMachine.getState();
        if(state != null){
            return StringUtils.collectionToCommaDelimitedString(state.getIds());
        }
        else {
          return "No state";
        }
    }

    @CliCommand(value = "sm start" , help = "Start a state machine")
    public String start(){
        stateMachine.start();
        return "State machine started";
    }

    @CliCommand(value = "sm stop" , help = "Stop a state machine")
    public String stop(){
        stateMachine.stop();
        return "State machine stopped";
    }

    @CliCommand(value = "sm variables" ,help = "Prints extended state variables")
    public String print(){
        StringBuilder buf = new StringBuilder();
        Set<Map.Entry<Object ,Object>> entrySet = stateMachine.getExtendedState()
                .getVariables().entrySet();
        Iterator<Map.Entry<Object,Object>> iterator = entrySet.iterator();
        if(entrySet.size() > 0){
            while (iterator.hasNext()){
                Map.Entry<Object ,Object> e = iterator.next();
                buf.append(e.getKey() + " = " + e.getValue());
                if(iterator.hasNext()){
                    buf.append("\n");
                }
            }
        }
        else {
            buf.append("No variables");
        }
        return buf.toString();
    }



}



























