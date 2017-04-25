package com.statemachine.examples.cdplayer.enumpk;

/**
 * Created by zn.wang on 17/4/25.
 */
public enum  EnumStates4CDPlayer {

    //super state of PLAYING and PAUSED
    BUSY,
    PLAYING,
    PAUSED,

    //super state of CLOSED and OPEN
    IDLE,//空闲
    CLOSED,
    OPEN;
}






