package com.statemachine.examples.cdplayer.Action;

import com.statemachine.examples.cdplayer.entity.Cd;
import com.statemachine.examples.cdplayer.enumpk.EnumEvents4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumHeaders;
import com.statemachine.examples.cdplayer.enumpk.EnumStates4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.Map;

/**
 * Created by zn.wang on 17/4/25.
 */
public class TrackAction implements Action<EnumStates4CDPlayer, EnumEvents4CDPlayer>{

    private static final Logger logger = LoggerFactory.getLogger(TrackAction.class);

    @Override
    public void execute(StateContext<EnumStates4CDPlayer, EnumEvents4CDPlayer> context) {
        Map<Object, Object> variables = context.getExtendedState().getVariables();
        Object trackshift = context.getMessageHeader(EnumHeaders.TRACK_SHIFT.toString());
        Object track = variables.get(EnumVariables.TRACK);
        Object cd = variables.get(EnumVariables.CD);
        if (trackshift instanceof Integer && track instanceof Integer && cd instanceof Cd) {
            int next = ((Integer)track) + ((Integer)trackshift);
            if (next >= 0 &&  ((Cd)cd).getTracks().length > next) {
                variables.put(EnumVariables.ELAPSED_TIME, 0l);
                variables.put(EnumVariables.TRACK, next);
            } else if (((Cd)cd).getTracks().length <= next) {
                context.getStateMachine().sendEvent(EnumEvents4CDPlayer.STOP);
            }
        }
    }
}







