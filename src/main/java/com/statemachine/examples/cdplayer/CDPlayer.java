package com.statemachine.examples.cdplayer;

import com.statemachine.examples.cdplayer.entity.Cd;
import com.statemachine.examples.cdplayer.enumpk.EnumEvents4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumHeaders;
import com.statemachine.examples.cdplayer.enumpk.EnumStates4CDPlayer;
import com.statemachine.examples.cdplayer.enumpk.EnumVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zn.wang on 17/4/25.
 */
@Component
public class CDPlayer {

    @Autowired
    private StateMachine<EnumStates4CDPlayer, EnumEvents4CDPlayer> stateMachine;

    private String cdStatus = "No CD";
    private String trackStatus = "";

    public void load(Cd cd) {
        stateMachine.sendEvent(MessageBuilder.withPayload(EnumEvents4CDPlayer.LOAD).setHeader(EnumVariables.CD.toString(), cd).build());
    }

    public void play() {
        stateMachine.sendEvent(EnumEvents4CDPlayer.PLAY);
    }

    public void stop() {
        stateMachine.sendEvent(EnumEvents4CDPlayer.STOP);
    }

    public void pause() {
        stateMachine.sendEvent(EnumEvents4CDPlayer.PAUSE);
    }

    public void eject() {
        stateMachine.sendEvent(EnumEvents4CDPlayer.EJECT);
    }

    public void forward() {
        stateMachine
                .sendEvent(MessageBuilder
                        .withPayload(EnumEvents4CDPlayer.FORWARD)
                        .setHeader(EnumHeaders.TRACK_SHIFT.toString(), 1).build());
    }

    public void back() {
        stateMachine
                .sendEvent(MessageBuilder
                        .withPayload(EnumEvents4CDPlayer.BACK)
                        .setHeader(EnumHeaders.TRACK_SHIFT.toString(), -1).build());
    }

    public String getLdcStatus() {
        return cdStatus + " " + trackStatus;
    }


   //@OnTransition(target = "BUSY")
    public void busy(ExtendedState extendedState) {
        Object cd = extendedState.getVariables().get(EnumVariables.CD);
        if (cd != null) {
            cdStatus = ((Cd)cd).getName();
        }
    }


    //@StatesOnTransition(target = EnumStates4CDPlayer.PLAYING)
    public void playing(ExtendedState extendedState) {
        Object elapsed = extendedState.getVariables().get(EnumVariables.ELAPSED_TIME);
        Object cd = extendedState.getVariables().get(EnumVariables.CD);
        Object track = extendedState.getVariables().get(EnumVariables.TRACK);
        if (elapsed instanceof Long && track instanceof Integer && cd instanceof Cd) {
            SimpleDateFormat format = new SimpleDateFormat("mm:ss");
            trackStatus = ((Cd) cd).getTracks()[((Integer) track)]
                    + " " + format.format(new Date((Long) elapsed));
        }
    }

    //@StatesOnTransition(target = EnumStates4CDPlayer.OPEN)
    public void open(ExtendedState extendedState) {
        cdStatus = "Open";
    }


    //@StatesOnTransition(target = {EnumStates4CDPlayer.CLOSED, EnumStates4CDPlayer.IDLE})
    public void closed(ExtendedState extendedState) {
        Object cd = extendedState.getVariables().get(EnumVariables.CD);
        if (cd != null) {
            cdStatus = ((Cd)cd).getName();
        } else {
            cdStatus = "No CD";
        }
        trackStatus = "";
    }

}













