package com.statemachine.examples.cdplayer.entity;

import java.io.Serializable;

/**
 * Created by zn.wang on 17/4/25.
 */
public class Track implements Serializable{
    private static final long serialVersionUID = 4841799591728475756L;
    private final String name;
    private final long length;

    public Track(String name, long length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return name;
    }

}
