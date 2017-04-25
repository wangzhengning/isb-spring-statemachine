package com.statemachine.examples.cdplayer.Action;

import com.statemachine.examples.cdplayer.enumpk.EnumEvents4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumStates4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * Created by zn.wang on 17/4/25.
 */
public class LoadAction implements Action<EnumStates4CDPlayer,EnumEvents4CDPlayer>{

    private static final Logger logger = LoggerFactory.getLogger(LoadAction.class);
    @Override
    public void execute(StateContext<EnumStates4CDPlayer, EnumEvents4CDPlayer> stateContext) {
        if(stateContext != null){
            Object cd = stateContext.getMessageHeader(EnumVariables.CD);
            stateContext.getExtendedState().getVariables().put(EnumVariables.CD ,cd);
        }
    }
}
