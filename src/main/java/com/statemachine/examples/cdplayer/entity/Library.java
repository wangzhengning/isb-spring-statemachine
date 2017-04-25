package com.statemachine.examples.cdplayer.entity;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zn.wang on 17/4/25.
 */
public class Library implements Serializable{

    private static final long serialVersionUID = -2658499622420302109L;
    private final List<Cd> collection;

    public Library(Cd[] collection){
        this.collection = Lists.newArrayList(collection);
    }

    public List<Cd> getCollection() {
        return collection;
    }

    public static Library buildSampleLibrary(){
        Track cd1Track = new Track("CD1 Track wzn_1" , 5*60 + 56);
        Track cd1Track2 = new Track("CD1 Track wzn_2" , 3*60 + 36);
        Cd cd1 = new Cd("Greatest Hits I" , new Track[]{cd1Track , cd1Track2});

        Track cd2Track = new Track("CD2 Track wzn_1" , 4*60 + 22);
        Track cd2Track2 = new Track("CD2 Track wzn_2" , 4*60 + 8);
        Cd cd2 = new Cd("Greatest Hits II" , new Track[]{cd2Track , cd2Track2});

        return new Library(new Cd[]{ cd1 , cd1 });
    }
}
