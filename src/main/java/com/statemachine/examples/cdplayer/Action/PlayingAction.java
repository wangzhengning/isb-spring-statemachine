package com.statemachine.examples.cdplayer.Action;

import com.statemachine.examples.cdplayer.entity.Cd;
import com.statemachine.examples.cdplayer.enumpk.EnumEvents4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumHeaders;
import com.statemachine.examples.cdplayer.enumpk.EnumStates4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.Map;

/**
 * Created by zn.wang on 17/4/25.
 */
public class PlayingAction implements Action<EnumStates4CDPlayer,EnumEvents4CDPlayer>{

    private static final Logger logger = LoggerFactory.getLogger(PlayingAction.class);

    @Override
    public void execute(StateContext<EnumStates4CDPlayer, EnumEvents4CDPlayer> context) {
        Map<Object, Object> variables = context.getExtendedState().getVariables();
        Object elapsed = variables.get(EnumVariables.ELAPSED_TIME);
        Object cd = variables.get(EnumVariables.CD);
        Object track = variables.get(EnumVariables.TRACK);
        if (elapsed instanceof Long) {
            long e = ((Long)elapsed) + 1000l;
            if (e > ((Cd) cd).getTracks()[((Integer) track)].getLength()*1000) {
                context.getStateMachine().sendEvent(MessageBuilder
                        .withPayload(EnumEvents4CDPlayer.FORWARD)
                        .setHeader(EnumHeaders.TRACK_SHIFT.toString(), 1).build());
            } else {
                variables.put(EnumVariables.ELAPSED_TIME, e);
            }
        }
    }
}
