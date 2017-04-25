package com.statemachine.examples.cdplayer.entity;


import java.io.Serializable;

/**
 * Created by zn.wang on 17/4/25.
 */
public class Cd implements Serializable{
    private static final long serialVersionUID = 7223534505906835591L;
    private final String name;
    private final Track[] tracks;

    public Cd(String name ,Track [] tracks){
        this.name = name;
        this.tracks = tracks;
    }

    public String getName() {
        return name;
    }

    public Track[] getTracks() {
        return tracks;
    }
}
