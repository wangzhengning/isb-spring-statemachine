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
public class ClosedEntryAction implements Action<EnumStates4CDPlayer,EnumEvents4CDPlayer>{

    private static final Logger logger = LoggerFactory.getLogger(ClosedEntryAction.class);

    @Override
    public void execute(StateContext<EnumStates4CDPlayer, EnumEvents4CDPlayer> stateContext) {
        if(stateContext != null
                && stateContext.getTransition() != null
                && stateContext.getEvent() == EnumEvents4CDPlayer.PLAY
                && stateContext.getTransition().getTarget().getId() == EnumStates4CDPlayer.CLOSED
                && stateContext.getExtendedState().getVariables().get(EnumVariables.CD) != null){
            stateContext.getStateMachine().sendEvent(EnumEvents4CDPlayer.PLAY);
        }
    }
}

















