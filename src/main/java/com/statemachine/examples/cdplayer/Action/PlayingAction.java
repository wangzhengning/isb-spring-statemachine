package com.statemachine.examples.cdplayer.Action;

import com.statemachine.examples.cdplayer.entity.Cd;
import com.statemachine.examples.cdplayer.entity.Track;
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
    public void execute(StateContext<EnumStates4CDPlayer, EnumEvents4CDPlayer> stateContext) {
        Map<Object ,Object> variables = stateContext.getExtendedState().getVariables();
        Object elapsedTime = variables.get(EnumVariables.ELAPSED_TIME);//运行时间
        Object cd = variables.get(EnumVariables.CD);
        Object track = variables.get(EnumVariables.TRACK);
        if(elapsedTime instanceof Long){
            long e = (Long) elapsedTime + 1000l;
            Track t = ((Cd) cd).getTracks()[(Integer) track];
            if(e > t.getLength() * 1000){
                stateContext.getStateMachine().sendEvent(MessageBuilder.withPayload(EnumEvents4CDPlayer.FORWARD)
                .setHeader(EnumHeaders.TRACK_SHIFT.toString() ,1).build());
            }else {
                variables.put(EnumVariables.ELAPSED_TIME , e);
            }
        }
    }
}
