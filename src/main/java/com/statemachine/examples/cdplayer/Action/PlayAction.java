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
public class PlayAction implements Action<EnumStates4CDPlayer,EnumEvents4CDPlayer>{

    private static final Logger logger = LoggerFactory.getLogger(PlayAction.class);

    @Override
    public void execute(StateContext<EnumStates4CDPlayer, EnumEvents4CDPlayer> context) {
        context.getExtendedState().getVariables().put(EnumVariables.ELAPSED_TIME, 0l);
        context.getExtendedState().getVariables().put(EnumVariables.TRACK, 0);
    }
}
