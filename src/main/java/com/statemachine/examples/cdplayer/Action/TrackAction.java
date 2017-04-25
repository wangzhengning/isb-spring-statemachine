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
    public void execute(StateContext<EnumStates4CDPlayer, EnumEvents4CDPlayer> stateContext) {
        Map<Object ,Object> variables = stateContext.getExtendedState().getVariables();
        Object trackShift = stateContext.getMessageHeader(EnumHeaders.TRACK_SHIFT);
        Object track = variables.get(EnumVariables.TRACK);
        Object cd = variables.get(EnumVariables.CD);
        if(trackShift instanceof Integer
                && track instanceof Integer
                && cd instanceof Cd){

            int next = (Integer) track + (Integer) trackShift;
            if(next > 0 && ((Cd)cd).getTracks().length > next){
                variables.put(EnumVariables.ELAPSED_TIME , 0l);
                variables.put(EnumVariables.TRACK , next);
            }
            else if(((Cd)cd).getTracks().length <= next){
                stateContext.getStateMachine().sendEvent(EnumEvents4CDPlayer.STOP);
            }
        }
    }
}







