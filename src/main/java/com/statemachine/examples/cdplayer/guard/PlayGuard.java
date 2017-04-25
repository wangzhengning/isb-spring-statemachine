package com.statemachine.examples.cdplayer.guard;

import com.statemachine.examples.cdplayer.enumpk.EnumEvents4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumStates4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

/**
 * Created by zn.wang on 17/4/25.
 */
public class PlayGuard implements Guard<EnumStates4CDPlayer, EnumEvents4CDPlayer>{

    private static final Logger logger = LoggerFactory.getLogger(PlayGuard.class);
    @Override
    public boolean evaluate(StateContext<EnumStates4CDPlayer, EnumEvents4CDPlayer> stateContext) {
        ExtendedState extendedState = stateContext.getExtendedState();
        return extendedState.getVariables().get(EnumVariables.CD) != null;
    }
}








